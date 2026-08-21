// Instancias globales para los modales
let modalTercero, modalPrograma, modalAsignatura;

$(document).ready(function() {
    // 1. Inicializar DataTables con configuración en español
    const dtOptions = {
        language: {
            processing: "Procesando...",
            search: "Buscar:",
            lengthMenu: "Mostrar _MENU_ registros",
            info: "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            infoEmpty: "Mostrando registros del 0 al 0 de un total de 0 registros",
            infoFiltered: "(filtrado de un total de _MAX_ registros)",
            zeroRecords: "No se encontraron resultados",
            emptyTable: "Ningún dato disponible en esta tabla",
            paginate: { first: "Primero", previous: "Anterior", next: "Siguiente", last: "Último" }
        }
    };

    if ($('#tablaTerceros').length) $('#tablaTerceros').DataTable(dtOptions);
    if ($('#tablaProgramas').length) $('#tablaProgramas').DataTable(dtOptions);
    if ($('#tablaAsignaturas').length) $('#tablaAsignaturas').DataTable(dtOptions);

    // 2. Instanciar modales de Bootstrap de forma segura
    const elTercero = document.getElementById('terceroModal');
    if (elTercero) modalTercero = new bootstrap.Modal(elTercero);

    const elPrograma = document.getElementById('programaModal');
    if (elPrograma) modalPrograma = new bootstrap.Modal(elPrograma);

    const elAsignatura = document.getElementById('asignaturaModal');
    if (elAsignatura) modalAsignatura = new bootstrap.Modal(elAsignatura);

    // 3. Activar el Reloj en Tiempo Real
    actualizarReloj();
    setInterval(actualizarReloj, 1000);

    // 4. Buscador en tiempo real para la tabla de informes de Pre-matrícula
    $("#searchInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#reportTable tbody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });
});

/* ==================== RELOJ EN TIEMPO REAL ==================== */
function actualizarReloj() {
    const clockElement = document.getElementById('liveClock');
    if (!clockElement) return;

    const ahora = new Date();
    const opcionesFecha = { weekday: 'long', day: '2-digit', month: 'long', year: 'numeric' };
    let fechaStr = ahora.toLocaleDateString('es-ES', opcionesFecha);
    
    fechaStr = fechaStr.charAt(0).toUpperCase() + fechaStr.slice(1);
    fechaStr = fechaStr.replace(/ de /g, ' de ').replace(/(\d{4})/, 'del $1');

    let horas = ahora.getHours();
    const minutos = String(ahora.getMinutes()).padStart(2, '0');
    const segundos = String(ahora.getSeconds()).padStart(2, '0');
    const ampm = horas >= 12 ? 'p.m.' : 'a.m.';
    
    horas = horas % 12;
    horas = horas ? horas : 12;
    const horasStr = String(horas).padStart(2, '0');

    const horaStr = `hora: ${horasStr}:${minutos}:${segundos} ${ampm}`;
    clockElement.innerText = `${fechaStr}, ${horaStr}`;
}

/* ==================== GESTIÓN DE TERCEROS ==================== */
function abrirModalRegistrar() {
    const form = document.getElementById('terceroForm');
    if (form) { form.reset(); form.action = '/terceros/guardar'; }
    document.getElementById('tercId').value = '';
    document.getElementById('modalTitle').innerText = 'Registrar Nuevo Tercero';
    const btn = document.getElementById('btnSubmit');
    if (btn) { btn.innerText = 'Guardar Tercero'; btn.className = 'btn btn-primary'; }
    if (modalTercero) modalTercero.show();
}

function editarTercero(id, tipoDoc, nroDoc, genero, nombres, apellidos, direccion, correo, movil, tipo) {
    document.getElementById('tercId').value = id;
    document.getElementById('tipoDoc').value = tipoDoc;
    document.getElementById('nroDoc').value = nroDoc;
    document.getElementById('genero').value = genero;
    document.getElementById('nombres').value = nombres;
    document.getElementById('apellidos').value = apellidos;
    document.getElementById('direccion').value = direccion;
    document.getElementById('correo').value = correo;
    document.getElementById('movil').value = movil;
    document.getElementById('tipo').value = tipo;

    const form = document.getElementById('terceroForm');
    if (form) form.action = '/terceros/actualizar';
    document.getElementById('modalTitle').innerText = 'Actualizar Tercero (ID: ' + id + ')';
    const btn = document.getElementById('btnSubmit');
    if (btn) { btn.innerText = 'Actualizar Cambios'; btn.className = 'btn btn-warning text-dark fw-bold'; }
    if (modalTercero) modalTercero.show();
}

/* ==================== GESTIÓN DE PROGRAMAS ==================== */
function abrirModalRegistrarPrograma() {
    const form = document.getElementById('programaForm');
    if (form) { form.reset(); form.action = '/programas/guardar'; }
    document.getElementById('progId').value = '';
    document.getElementById('modalTitleProg').innerText = 'Registrar Nuevo Programa';
    const btn = document.getElementById('btnSubmitProg');
    if (btn) { btn.innerText = 'Guardar Programa'; btn.className = 'btn btn-primary'; }
    if (modalPrograma) modalPrograma.show();
}

function editarPrograma(btn) {
    const id = btn.getAttribute('data-id');
    const nombre = btn.getAttribute('data-programa');

    document.getElementById('progId').value = id;
    document.getElementById('progNombre').value = nombre;

    const form = document.getElementById('programaForm');
    if (form) form.action = '/programas/actualizar';

    document.getElementById('modalTitleProg').innerText = 'Actualizar Programa (ID: ' + id + ')';

    const btnSubmit = document.getElementById('btnSubmitProg');
    if (btnSubmit) {
        btnSubmit.innerText = 'Actualizar Cambios';
        btnSubmit.className = 'btn btn-warning text-dark fw-bold';
    }

    if (modalPrograma) modalPrograma.show();
}

/* ==================== GESTIÓN DE ASIGNATURAS ==================== */
function abrirModalRegistrarAsignatura() {
    const form = document.getElementById('asignaturaForm');
    if (form) { 
        form.reset(); 
        form.action = '/asignaturas/guardar'; 
    }
    
    const idField = document.getElementById('asigId');
    if (idField) idField.value = ''; 
    
    document.getElementById('modalTitleAsig').innerText = 'Registrar Nueva Asignatura';
    
    const btnSubmit = document.getElementById('btnSubmitAsig');
    if (btnSubmit) {
        btnSubmit.innerText = 'Guardar Asignatura';
        btnSubmit.className = 'btn btn-primary';
    }
    
    if (modalAsignatura) modalAsignatura.show();
}

function editarAsignatura(btn) {
    const id = btn.getAttribute('data-id');
    const nombre = btn.getAttribute('data-nombre');
    const creditos = btn.getAttribute('data-creditos');
    const codigo = btn.getAttribute('data-codigo');

    document.getElementById('asigId').value = id;
    document.getElementById('asigNombre').value = nombre;
    document.getElementById('asigCreditos').value = creditos;
    document.getElementById('asigCodigo').value = codigo;

    const form = document.getElementById('asignaturaForm');
    if (form) form.action = '/asignaturas/actualizar';

    document.getElementById('modalTitleAsig').innerText = 'Actualizar Asignatura (ID: ' + id + ')';
    const btnSubmit = document.getElementById('btnSubmitAsig');
    if (btnSubmit) {
        btnSubmit.innerText = 'Actualizar Cambios';
        btnSubmit.className = 'btn btn-warning text-dark fw-bold';
    }

    if (modalAsignatura) modalAsignatura.show();
}

/* ==========================================
 * ==================== GESTIÓN DE MATRÍCULAS ==================== 
 * ========================================== */
document.addEventListener("DOMContentLoaded", function() {
    const terceroSelect = document.getElementById("terceroId");
    if (!terceroSelect) return;

    const programaSelect = document.getElementById("programaId");
    const pensumSelect = document.getElementById("pensumId");
    const mensaje = document.getElementById("mensaje");
    const btnMatricular = document.getElementById("btnMatricular");
    const promedioContainer = document.getElementById("promedioContainer");
    const promedioValor = document.getElementById("promedioValor");
    const formMatricula = terceroSelect.closest("form");

    terceroSelect.addEventListener("change", function() {
        const estudianteId = this.value;
        
        promedioContainer.classList.add("d-none");
        promedioContainer.classList.remove("alert-danger", "alert-info", "alert-warning");
        mensaje.textContent = "";
        btnMatricular.disabled = false;

        if(estudianteId) {
            fetch(`/matriculas/promedio/${estudianteId}`)
                .then(response => response.json())
                .then(promedio => {
                    promedioContainer.classList.remove("d-none");
                    promedioValor.textContent = promedio.toFixed(2);
                    
                    if(promedio > 0 && promedio < 3.0) {
                        promedioContainer.classList.add("alert-danger");
                        mensaje.textContent = "⚠️ Atención: El estudiante se encuentra en prueba condicional por promedio inferior a 3.0.";
                        mensaje.className = "mt-4 text-center fw-bold text-danger";
                    } else if (promedio === 0) {
                        promedioContainer.classList.add("alert-warning");
                        mensaje.textContent = "Estudiante nuevo (Sin promedio calculado).";
                        mensaje.className = "mt-4 text-center fw-bold text-warning";
                    } else {
                        promedioContainer.classList.add("alert-info");
                        mensaje.textContent = "✅ Estudiante apto para pre-matrícula.";
                        mensaje.className = "mt-4 text-center fw-bold text-primary";
                    }
                })
                .catch(err => console.error("Error al obtener promedio:", err));
        }
    });

    if(programaSelect) {
        programaSelect.addEventListener("change", function () {
            const programaId = this.value;
            pensumSelect.innerHTML = '<option value="">Cargando pensums...</option>';
            pensumSelect.disabled = true;
            mensaje.textContent = "";

            if (!programaId) {
                pensumSelect.innerHTML = '<option value="">Seleccione primero un programa</option>';
                return;
            }

            fetch(`/matriculas/pensums/${programaId}`)
                .then(response => {
                    if (!response.ok) throw new Error("Error en red");
                    return response.json();
                })
                .then(pensums => {
                    pensumSelect.innerHTML = '<option value="">Seleccione un pensum</option>';
                    if (pensums.length === 0) {
                        pensumSelect.innerHTML = '<option value="">No hay pensums disponibles</option>';
                        mensaje.textContent = "El programa seleccionado no tiene pensums registrados.";
                        mensaje.className = "mt-4 text-center fw-bold text-danger";
                        pensumSelect.disabled = true;
                        return;
                    }
                    pensums.forEach(pensum => {
                        const option = document.createElement("option");
                        option.value = pensum.id;
                        option.textContent = `${pensum.id} - Periodo ${pensum.periodo}`;
                        pensumSelect.appendChild(option);
                    });
                    pensumSelect.disabled = false;
                })
                .catch(error => {
                    pensumSelect.innerHTML = '<option value="">Error cargando pensums</option>';
                    pensumSelect.disabled = true;
                    mensaje.textContent = "Ocurrió un error al cargar los pensums.";
                    mensaje.className = "mt-4 text-center fw-bold text-danger";
                });
        });
    }

    if (formMatricula) {
        formMatricula.addEventListener("submit", function (event) {
            if (!terceroSelect.value || !programaSelect.value || !pensumSelect.value) {
                event.preventDefault();
                alert("Debe completar todos los campos obligatorios (Estudiante, Programa y Pensum).");
            }
        });
    }
});