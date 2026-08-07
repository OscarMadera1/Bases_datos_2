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

    // 2. Instanciar modales de Bootstrap
    const elTercero = document.getElementById('terceroModal');
    if (elTercero) modalTercero = new bootstrap.Modal(elTercero);

    const elPrograma = document.getElementById('programaModal');
    if (elPrograma) modalPrograma = new bootstrap.Modal(elPrograma);

    const elAsignatura = document.getElementById('asignaturaModal');
    if (elAsignatura) modalAsignatura = new bootstrap.Modal(elAsignatura);

    // 3. Activar el Reloj en Tiempo Real
    actualizarReloj();
    setInterval(actualizarReloj, 1000);
});

/* ==================== RELOJ EN TIEMPO REAL ==================== */
function actualizarReloj() {
    const clockElement = document.getElementById('liveClock');
    if (!clockElement) return; // Si no existe en la página actual, no hace nada

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

function editarPrograma(id, nombre) {
    document.getElementById('progId').value = id;
    document.getElementById('progNombre').value = nombre;
    const form = document.getElementById('programaForm');
    if (form) form.action = '/programas/actualizar';
    document.getElementById('modalTitleProg').innerText = 'Actualizar Programa (ID: ' + id + ')';
    const btn = document.getElementById('btnSubmitProg');
    if (btn) { btn.innerText = 'Actualizar Cambios'; btn.className = 'btn btn-warning text-dark fw-bold'; }
    if (modalPrograma) modalPrograma.show();
}

/* ==================== GESTIÓN DE ASIGNATURAS ==================== */
function abrirModalRegistrarAsignatura() {
    const form = document.getElementById('asignaturaForm');
    if (form) { form.reset(); form.action = '/asignaturas/guardar'; }
    document.getElementById('asigCodigo').value = '';
    document.getElementById('modalTitleAsig').innerText = 'Registrar Nueva Asignatura';
    const btn = document.getElementById('btnSubmitAsig');
    if (btn) { btn.innerText = 'Guardar Asignatura'; btn.className = 'btn btn-primary'; }
    if (modalAsignatura) modalAsignatura.show();
}

function editarAsignatura(codigo, nombre, creditos, codigoMat) {
    document.getElementById('asigId').value = codigo;
    document.getElementById('asigNombre').value = nombre;
    document.getElementById('asigCreditos').value = creditos;
    document.getElementById('asigCodigo').value = codigoMat;
    const form = document.getElementById('asignaturaForm');
    if (form) form.action = '/asignaturas/actualizar';
    document.getElementById('modalTitleAsig').innerText = 'Actualizar Asignatura (ID: ' + codigo + ')';
    const btn = document.getElementById('btnSubmitAsig');
    if (btn) { btn.innerText = 'Actualizar Cambios'; btn.className = 'btn btn-warning text-dark fw-bold'; }
    if (modalAsignatura) modalAsignatura.show();
}