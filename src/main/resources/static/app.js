// para terceros

document.addEventListener('DOMContentLoaded', () => {
    const formTercero = document.getElementById('form-tercero');

    // Función para guardar un tercero
    formTercero.addEventListener('submit', async (e) => {
        e.preventDefault();

        // Recolectar datos del formulario
        const nuevoTercero = {
            id: document.getElementById('tercId').value,
            tipoDoc: document.getElementById('tercTipoDoc').value,
            nroDoc: document.getElementById('tercNroDoc').value,
            genero: document.getElementById('tercGenero').value,
            nombres: document.getElementById('tercNombres').value,
            apellidos: document.getElementById('tercApellidos').value,
            direccion: document.getElementById('tercDirec').value,
            correo: document.getElementById('tercCorreo').value,
            movil: document.getElementById('tercMovil').value,
            tipo: document.getElementById('tercTipo').value
        };

        try {
            // Aquí haces el llamado POST a tu controlador en Spring Boot
            // Ejemplo: const response = await fetch('/api/terceros', { ... })
            
            console.log("Datos listos para enviar a la base de datos Oracle:", nuevoTercero);
            alert("Tercero registrado correctamente (Simulación)");
            formTercero.reset();
            
            // cargarTerceros(); // Actualizar la tabla después de guardar
        } catch (error) {
            console.error('Error al guardar el tercero:', error);
        }
    });

    // Función para cargar los terceros en la tabla (Ejemplo base)
    async function cargarTerceros() {
        try {
            // const response = await fetch('/api/terceros');
            // const data = await response.json();
            
            // Simulación de datos que llegarían de Oracle
            const data = [
                { id: 1, doc: 'CC - 100234', nombres: 'Juan', apellidos: 'Pérez', correo: 'juan@email.com' }
            ];

            const tbody = document.querySelector('#tabla-terceros tbody');
            tbody.innerHTML = '';

            data.forEach(tercero => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${tercero.id}</td>
                    <td>${tercero.doc}</td>
                    <td>${tercero.nombres}</td>
                    <td>${tercero.apellidos}</td>
                    <td>${tercero.correo}</td>
                    <td>
                        <button onclick="eliminar(${tercero.id})">Eliminar</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        } catch (error) {
            console.error('Error al cargar terceros:', error);
        }
    }

    // Cargar datos al iniciar la página
    cargarTerceros();
});

// para programas

document.addEventListener('DOMContentLoaded', () => {
    // --- LÓGICA PARA PROGRAMAS ---
    const formPrograma = document.getElementById('form-programa');

    // Validamos que estemos en la página de programas
    if (formPrograma) {
        
        // Función para guardar un programa
        formPrograma.addEventListener('submit', async (e) => {
            e.preventDefault();

            // Recolectar datos del formulario
            const nuevoPrograma = {
                id: document.getElementById('progId').value,
                programa: document.getElementById('progPrograma').value
            };

            try {
                // Aquí harás el llamado POST a tu controlador Spring Boot
                // Ejemplo: await fetch('/api/programas', { method: 'POST', body: JSON.stringify(nuevoPrograma) ... })
                
                console.log("Datos listos para enviar a Oracle (PROGRAMAS):", nuevoPrograma);
                alert("Programa registrado correctamente (Simulación)");
                formPrograma.reset();
                
                // cargarProgramas(); // Actualizar la tabla
            } catch (error) {
                console.error('Error al guardar el programa:', error);
            }
        });

        // Función para cargar los programas en la tabla
        async function cargarProgramas() {
            try {
                // Simulación de datos que llegarían de tu base de datos Oracle
                const data = [
                    { id: 101, programa: 'Ingeniería de Sistemas' },
                    { id: 102, programa: 'Ingeniería Industrial' },
                    { id: 103, programa: 'Administración de Empresas' }
                ];

                const tbody = document.querySelector('#tabla-programas tbody');
                tbody.innerHTML = '';

                data.forEach(prog => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${prog.id}</td>
                        <td>${prog.programa}</td>
                        <td>
                            <button onclick="eliminarPrograma(${prog.id})">Eliminar</button>
                        </td>
                    `;
                    tbody.appendChild(tr);
                });
            } catch (error) {
                console.error('Error al cargar programas:', error);
            }
        }

        // Cargar datos al iniciar la página
        cargarProgramas();
    }
});

// Función global simulada para eliminar (aplica para cualquier script en la página)
function eliminarPrograma(id) {
    if(confirm(`¿Estás seguro de eliminar el programa con ID ${id}?`)) {
        console.log(`Petición DELETE enviada para el ID: ${id}`);
        // Aquí iría tu fetch con método DELETE
    }
}

// para asignaturas
document.addEventListener('DOMContentLoaded', () => {
    // --- LÓGICA PARA ASIGNATURAS ---
    const formAsignatura = document.getElementById('form-asignatura');

    if (formAsignatura) {
        
        // Función para guardar una asignatura
        formAsignatura.addEventListener('submit', async (e) => {
            e.preventDefault();

            // Recolectar datos del formulario
            const nuevaAsignatura = {
                id: document.getElementById('asigId').value,
                asignatura: document.getElementById('asigAsignatura').value,
                creditos: document.getElementById('asigCreditos').value,
                codigo: document.getElementById('asigCodigo').value
            };

            try {
                // Petición POST a Spring Boot
                // Ejemplo: await fetch('/api/asignaturas', { method: 'POST', body: JSON.stringify(nuevaAsignatura) ... })
                
                console.log("Datos listos para enviar a Oracle (ASIGNATURAS):", nuevaAsignatura);
                alert("Asignatura registrada correctamente (Simulación)");
                formAsignatura.reset();
                
                // cargarAsignaturas(); // Actualizar la tabla
            } catch (error) {
                console.error('Error al guardar la asignatura:', error);
            }
        });

        // Función para cargar las asignaturas en la tabla
        async function cargarAsignaturas() {
            try {
                // Simulación de datos que llegarían de tu BD Oracle
                const data = [
                    { id: 1, asignatura: 'Base de Datos II', creditos: 4, codigo: 'BD2001' },
                    { id: 2, asignatura: 'Ingeniería de Software', creditos: 3, codigo: 'ISW100' },
                    { id: 3, asignatura: 'Cálculo Diferencial', creditos: 4, codigo: 'CAL001' }
                ];

                const tbody = document.querySelector('#tabla-asignaturas tbody');
                tbody.innerHTML = '';

                data.forEach(asig => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${asig.id}</td>
                        <td>${asig.asignatura}</td>
                        <td>${asig.creditos}</td>
                        <td>${asig.codigo}</td>
                        <td>
                            <button onclick="eliminarAsignatura(${asig.id})">Eliminar</button>
                        </td>
                    `;
                    tbody.appendChild(tr);
                });
            } catch (error) {
                console.error('Error al cargar asignaturas:', error);
            }
        }

        // Cargar datos iniciales
        cargarAsignaturas();
    }
});

// Función global simulada para eliminar
function eliminarAsignatura(id) {
    if(confirm(`¿Estás seguro de eliminar la asignatura con ID ${id}?`)) {
        console.log(`Petición DELETE enviada para el ID: ${id}`);
        // Aquí iría tu fetch DELETE apuntando a tu API
    }
}

