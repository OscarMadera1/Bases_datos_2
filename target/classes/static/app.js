// Instancias globales para los modales
let modalTercero, modalPrograma, modalAsignatura;

$(document).ready(function () {
    // =========================================================================
    // INICIALIZACIÓN BÁSICA
    // =========================================================================
    const dtOptions = {
        language: {
            processing: "Procesando...",
            search: "Buscar:",
            lengthMenu: "Mostrar _MENU_ registros",
            info: "Mostrando del _START_ al _END_ de _TOTAL_ registros",
            infoEmpty: "Mostrando 0 registros",
            infoFiltered: "(filtrado de _MAX_ registros)",
            zeroRecords: "No se encontraron resultados",
            emptyTable: "Ningún dato disponible en esta tabla",
            paginate: { first: "Primero", previous: "Anterior", next: "Siguiente", last: "Último" }
        }
    };

    if ($('#tablaTerceros').length) $('#tablaTerceros').DataTable(dtOptions);
    if ($('#tablaProgramas').length) $('#tablaProgramas').DataTable(dtOptions);
    if ($('#tablaAsignaturas').length) $('#tablaAsignaturas').DataTable(dtOptions);

    const elTercero = document.getElementById('terceroModal');
    if (elTercero) modalTercero = new bootstrap.Modal(elTercero);
    const elPrograma = document.getElementById('programaModal');
    if (elPrograma) modalPrograma = new bootstrap.Modal(elPrograma);
    const elAsignatura = document.getElementById('asignaturaModal');
    if (elAsignatura) modalAsignatura = new bootstrap.Modal(elAsignatura);

    actualizarReloj();
    setInterval(actualizarReloj, 1000);

    // =========================================================================
    // MÓDULO: INFORMES (Buscadores)
    // =========================================================================
    // Buscador general para tablas (Auditoría, Promedios)
    $("#searchInput").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#reportTable tbody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });

    // Nuevo buscador para la lista estilizada de Materias Faltantes (Informes -> Pre-matrícula)
    $('#buscadorInformes').off('keyup').on('keyup', function () {
        let valor = $(this).val().toLowerCase();
        $('#listaMateriasInformes .asignatura-item').each(function () {
            let texto = $(this).text().toLowerCase();
            $(this).toggle(texto.indexOf(valor) > -1);
        });
    });

    // =========================================================================
    // MÓDULO: PROMEDIOS
    // =========================================================================
    $("#searchInputPromedios").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#tablaGeneralPromedios tbody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });

    $('#btnCalcularPromedioPantalla').click(function () {
        let estudianteId = $('#terceroPromedioId').val();
        if (!estudianteId) { alert('Seleccione una opción.'); return; }

        let btn = $(this);
        let textoOriginal = btn.text();
        btn.text('Consultando...').prop('disabled', true);

        let contenedor = $('#resultadoPromedioContainer');
        contenedor.removeClass('d-none alert-success alert-danger alert-info');

        if (estudianteId === 'todos') {
            $('#resultadoPromedioValor').html('<span class="fs-5 text-primary">Consultando V_PROMEDIOS...</span>');
            contenedor.addClass('alert-info');
            setTimeout(() => window.location.reload(), 1500);
            return;
        }

        $.get('/api/promedios/' + estudianteId, function (data) {
            $('#resultadoPromedioValor').text(parseFloat(data).toFixed(2));
            contenedor.addClass(data >= 3.0 ? 'alert-success' : 'alert-danger');
            btn.text(textoOriginal).prop('disabled', false);
        }).fail(function () {
            alert('Error al ejecutar la función en la Base de Datos.');
            btn.text(textoOriginal).prop('disabled', false);
        });
    });

    // =========================================================================
    // MÓDULO: ASIGNAR PENSUM (Formulario de vinculación)
    // =========================================================================
    
    // 1. Llenar pensums al elegir programa (ID's específicos de Asignar Pensum)
    $('#programaId').change(function () {
        const programaId = $(this).val();
        const pensumSelect = $('#pensumId');

        pensumSelect.empty().append('<option value="">Cargando pensums...</option>').prop('disabled', true);

        if (!programaId) {
            pensumSelect.empty().append('<option value="">Seleccione primero un programa</option>');
            return;
        }

        $.get(`/prematricula/pensums/${programaId}`, function (pensums) {
            pensumSelect.empty().append('<option value="">Seleccione un pensum</option>');
            if (pensums.length === 0) {
                pensumSelect.empty().append('<option value="">No hay pensums disponibles</option>');
                return;
            }
            pensums.forEach(p => pensumSelect.append(`<option value="${p.id}">Pensum ID: ${p.id} - Periodo: ${p.periodo}</option>`));
            pensumSelect.prop('disabled', false);
        });
    });

    // 2. Botón de Ejecutar Asignación
    $('#btnMatricularAccion').click(function () {
        const estudianteId = $("#terceroId").val();
        const programaId = $("#programaId").val();
        const pensumId = $("#pensumId").val();

        if (!estudianteId || !programaId || !pensumId) {
            alert("Por favor seleccione el estudiante, el programa y el pensum.");
            return;
        }

        let btn = $(this);
        let textoOriginal = btn.text();
        btn.text("Asignando...").prop('disabled', true);

        $.post('/prematricula/api/asignar-pensum', { estudianteId, pensumId })
            .done(function (respuesta) {
                $.get(`/prematricula/api/terc-pensum/${estudianteId}`, function (data) {
                    let html = "";
                    if (data && data.length > 0) {
                        data.forEach(item => {
                            html += `<tr>
                                <td class="fw-bold">${item.pensId}</td>
                                <td>${item.tercId}</td>
                                <td>${item.tepePeriodo}</td>
                                <td><button type="button" class="btn btn-dark btn-sm fw-bold px-3" onclick="examinarDetalle(${item.tercId})">Examina</button></td>
                            </tr>`;
                        });
                    } else {
                        html = `<tr><td colspan="4" class="text-center text-muted">Aún no hay registros.</td></tr>`;
                    }
                    $("#tbodyTercPensum").html(html);
                    $("#divTercPensum").removeClass("d-none");
                    $("#divDetalleAsignaturas").addClass("d-none");
                    btn.text(textoOriginal).prop('disabled', false);
                });
            })
            .fail(function (jqXHR) {
                alert(jqXHR.responseText || "Error de comunicación con el servidor.");
                btn.text(textoOriginal).prop('disabled', false);
            });
    });

    // =========================================================================
    // MÓDULO: PRE-MATRÍCULA (Verificación de materias faltantes)
    // =========================================================================
    
    // 1. Llenar pensums al elegir programa (ID's específicos de Pre-matrícula)
    $('#programaSelect').change(function () {
        const programaId = $(this).val();
        const pensumSelect = $('#pensumSelect');

        pensumSelect.empty().append('<option value="">Cargando pensums...</option>').prop('disabled', true);

        if (!programaId) {
            pensumSelect.empty().append('<option value="">Esperando programa...</option>');
            return;
        }

        $.get(`/prematricula/pensums/${programaId}`)
            .done(function (pensums) {
                pensumSelect.empty().append('<option value="">Seleccione un pensum...</option>');
                if (pensums.length === 0) {
                    pensumSelect.empty().append('<option value="">No hay pensums asignados</option>');
                    return;
                }
                pensums.forEach(p => pensumSelect.append(`<option value="${p.id}">Pensum: ${p.id} - Período: ${p.periodo}</option>`));
                pensumSelect.prop('disabled', false);
            })
            .fail(function () {
                pensumSelect.empty().append('<option value="">Error al cargar pensums</option>');
            });
    });

    // 2. Botón de Consultar Malla Faltante
    $('#btnConsultarFaltantes').click(function () {
        const estudianteId = $('#estudianteSelect').val();
        const estudianteTexto = $('#estudianteSelect option:selected').text();
        const pensumId = $('#pensumSelect').val();

        if (!estudianteId || !pensumId) {
            alert('Debe seleccionar el estudiante y el pensum.');
            return;
        }

        let btn = $(this);
        let txtOrig = btn.text();
        btn.text('Consultando...').prop('disabled', true);

        $.get(`/prematricula/api/materias-faltantes`, { pensumId, estudianteId })
            .done(function (data) {
                $('#nombreEstudianteRes').text(estudianteTexto.substring(estudianteTexto.indexOf('-') + 1).trim());
                $('#totalPendientesBadge').text(data.length + ' Pendientes');
                
                renderizarMateriasFaltantes(data);
                
                $('#areaResultados').removeClass('d-none');
                btn.text(txtOrig).prop('disabled', false);
            })
            .fail(function () {
                alert('Error al consultar las materias faltantes.');
                btn.text(txtOrig).prop('disabled', false);
            });
    });

    // 3. Buscador en tiempo real de materias faltantes
    $('#buscadorMaterias').off('keyup').on('keyup', function () {
        let valor = $(this).val().toLowerCase();
        $('.asignatura-item').each(function () {
            let texto = $(this).text().toLowerCase();
            $(this).toggle(texto.indexOf(valor) > -1);
        });
    });

}); // <--- FIN DEL DOCUMENT READY. Todo se procesa aquí dentro.

// =========================================================================
// FUNCIONES GLOBALES (Llamadas desde el HTML o internas)
// =========================================================================

function actualizarReloj() {
    const clockElement = document.getElementById('liveClock');
    if (!clockElement) return;
    const ahora = new Date();
    const opciones = { weekday: 'long', day: '2-digit', month: 'long', year: 'numeric' };
    let fStr = ahora.toLocaleDateString('es-ES', opciones);
    fStr = fStr.charAt(0).toUpperCase() + fStr.slice(1).replace(/ de /g, ' de ').replace(/(\d{4})/, 'del $1');
    let h = ahora.getHours(), m = String(ahora.getMinutes()).padStart(2, '0'), s = String(ahora.getSeconds()).padStart(2, '0');
    let ampm = h >= 12 ? 'p.m.' : 'a.m.';
    h = h % 12 || 12;
    clockElement.innerText = `${fStr}, hora: ${String(h).padStart(2, '0')}:${m}:${s} ${ampm}`;
}

// Dibujar Acordeón
function renderizarMateriasFaltantes(materias) {
    const contenedor = $('#contenedorMaterias');
    const contenedorBotones = $('#botonesFiltroNivel');
    
    contenedor.empty();
    contenedorBotones.empty();

    if (materias.length === 0) {
        contenedor.html('<div class="alert alert-success fw-bold text-center">¡El estudiante ha completado todas las materias de este pensum!</div>');
        return;
    }

    const porNivel = materias.reduce((acc, mat) => {
        (acc[mat.nivel] = acc[mat.nivel] || []).push(mat);
        return acc;
    }, {});

    contenedorBotones.append(`<button class="btn btn-outline-primary btn-sm me-1 filtro-btn active" data-nivel="todos">Todos</button>`);

    Object.keys(porNivel).forEach(nivel => {
        contenedorBotones.append(`<button class="btn btn-outline-primary btn-sm me-1 filtro-btn" data-nivel="${nivel}">Semestre ${nivel}</button>`);
        
        let html = `
            <div class="grupo-semestre" data-nivel="${nivel}">
                <div class="semestre-header mt-3" onclick="$(this).next('.semestre-body').slideToggle();">
                    <span>Semestre ${nivel}</span><span>▼</span>
                </div>
                <div class="semestre-body">
        `;
        porNivel[nivel].forEach(asig => {
            html += `
                <div class="asignatura-item">
                    <div>
                        <span class="badge bg-secondary me-2">${asig.codigo}</span>
                        <span class="text-uppercase fw-bold text-dark">${asig.nombre}</span>
                    </div>
                    <div class="text-muted small fw-bold">Créditos: ${asig.creditos}</div>
                </div>
            `;
        });
        html += `</div></div>`;
        contenedor.append(html);
    });

    $('.filtro-btn').click(function () {
        $('.filtro-btn').removeClass('active');
        $(this).addClass('active');
        let niv = $(this).data('nivel');
        if (niv === 'todos') $('.grupo-semestre').show();
        else { $('.grupo-semestre').hide(); $(`.grupo-semestre[data-nivel="${niv}"]`).show(); }
    });
}

function examinarDetalle(estudianteId) {
    $.get(`/prematricula/api/detalle-asignaturas/${estudianteId}`, function (data) {
        let html = "";
        let total = data ? data.length : 0;
        if (total > 0) {
            data.forEach(det => {
                html += `<tr>
                    <td class="fw-bold text-primary">${det.periodo}</td>
                    <td>${det.docenteAsignado}</td>
                    <td>${det.asigNombre}</td>
                </tr>`;
            });
        } else {
            html = `<tr><td colspan="3" class="text-center text-muted py-3">No hay asignaturas registradas.</td></tr>`;
        }
        $("#tbodyDetalleAsignaturas").html(html);
        $("#badgeTotalAsignaturas").text(total);
        $("#divDetalleAsignaturas").removeClass("d-none");
        document.getElementById("divDetalleAsignaturas").scrollIntoView({ behavior: 'smooth' });
    });
}