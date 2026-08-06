// para terceros

// Inicialización global de DataTables y gestión del Modal de Terceros
let myModal;

$(document).ready(function() {
    // Configurar y traducir DataTable al español
    $('#tablaTerceros').DataTable({
        language: {
            processing: "Procesando...",
            search: "Buscar:",
            lengthMenu: "Mostrar _MENU_ registros",
            info: "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            infoEmpty: "Mostrando registros del 0 al 0 de un total de 0 registros",
            infoFiltered: "(filtrado de un total de _MAX_ registros)",
            zeroRecords: "No se encontraron resultados",
            emptyTable: "Ningún dato disponible en esta tabla",
            paginate: {
                first: "Primero",
                previous: "Anterior",
                next: "Siguiente",
                last: "Último"
            },
            aria: {
                sortAscending: ": Activar para ordenar la columna de manera ascendente",
                sortDescending: ": Activar para ordenar la columna de manera descendente"
            }
        }
    });

    // Instanciar el modal de Bootstrap de forma segura
    const modalElement = document.getElementById('terceroModal');
    if (modalElement) {
        myModal = new bootstrap.Modal(modalElement);
    }
});

// Función para abrir el modal en modo Registro limpio
function abrirModalRegistrar() {
    const form = document.getElementById('terceroForm');
    if (form) {
        form.reset();
        form.action = '/terceros/guardar';
    }
    
    const tercId = document.getElementById('tercId');
    if (tercId) tercId.value = '';

    const modalTitle = document.getElementById('modalTitle');
    if (modalTitle) modalTitle.innerText = 'Registrar Nuevo Tercero';

    const btnSubmit = document.getElementById('btnSubmit');
    if (btnSubmit) {
        btnSubmit.innerText = 'Guardar Tercero';
        btnSubmit.className = 'btn btn-primary';
    }

    if (myModal) myModal.show();
}

// Función para cargar los datos en el modal al hacer clic en Editar
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
    if (form) {
        form.action = '/terceros/actualizar';
    }

    const modalTitle = document.getElementById('modalTitle');
    if (modalTitle) modalTitle.innerText = 'Actualizar Tercero (ID: ' + id + ')';

    const btnSubmit = document.getElementById('btnSubmit');
    if (btnSubmit) {
        btnSubmit.innerText = 'Actualizar Cambios';
        btnSubmit.className = 'btn btn-warning text-dark fw-bold';
    }

    if (myModal) myModal.show();
}