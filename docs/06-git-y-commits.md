# 06. Git y commits

En las pruebas anteriores se revisaron los commits de cada integrante y la cantidad cuenta. Haz commits pequeños, frecuentes y con mensaje claro, desde tu propia cuenta de GitHub.

## Antes de empezar

```
git config --global user.name "Tu Nombre"
git config --global user.email "tu-correo@ejemplo.com"
```

El correo debe ser el de tu cuenta de GitHub para que los commits aparezcan a tu nombre.

## Crear el repositorio de la prueba

1. En GitHub: New repository, nombre claro (por ejemplo `Prueba-Arboles-Apellido`), público o como indique el docente, sin README.
2. En tu computador:

```
mkdir Prueba-Arboles-Apellido
cd Prueba-Arboles-Apellido
git init
git branch -M main
git remote add origin https://github.com/TU-USUARIO/Prueba-Arboles-Apellido.git
```

## Secuencia de commits recomendada

| Orden | Qué incluye | Mensaje de ejemplo |
|---|---|---|
| 1 | `README.md`, `.gitignore`, carpetas vacías con `.gitkeep` | `Estructura inicial del proyecto` |
| 2 | `enums` y `modelo` (entidades con validaciones) | `Agrega entidades del modelo con validaciones` |
| 3 | `estructuras` y `Nodo`, `NodoGeneral` | `Agrega nodos y estructuras base` |
| 4 | `ArbolBinario` | `Implementa árbol binario con recorridos y estadísticas` |
| 5 | `ArbolBinarioBusqueda` | `Implementa ABB con insertar, buscar y eliminar` |
| 6 | `ArbolGeneral` y `Transformador` | `Implementa árbol general y transformación a binario` |
| 7 | Gestores de negocio | `Agrega gestores de negocio con reglas del enunciado` |
| 8 | `DatosPrueba` | `Agrega datos de prueba` |
| 9 | `Main` | `Agrega menú de consola` |
| 10 | Pruebas | `Agrega pruebas de casos límite` |
| 11 | Informe y capturas | `Agrega informe y capturas de ejecución` |

Después de cada commit haz `git push`, así queda respaldado y con la hora real.

## Comandos de todos los días

```
git status
git add src/modelo/Libro.java
git commit -m "Agrega entidad Libro con validaciones"
git push -u origin main
```

La primera vez usa `git push -u origin main`; después basta con `git push`.

Agregar todo lo que cambió:

```
git add .
git commit -m "Mensaje claro"
```

Ver la historia:

```
git log --oneline
```

## Mensajes de commit

- En español, en presente, en una línea de máximo 70 caracteres: `Implementa eliminación de ABB con tres casos`.
- Un tema por commit. Evita `cambios`, `arreglos` o `final`.
- Prefijos opcionales: `feat:`, `fix:`, `docs:`, `test:`.

Ejemplos buenos:

- `Corrige eliminación de nodo con un solo hijo derecho`
- `Agrega regla A5: solo se eliminan unidades sin subunidades`
- `Agrega pruebas de árbol vacío y clave repetida`

## `.gitignore` mínimo

```
out/
bin/
*.class
.idea/
*.iml
.vscode/
fuentes.txt
```

## Problemas frecuentes

| Problema | Solución |
|---|---|
| `git push` pide usuario y contraseña | GitHub ya no acepta contraseña: usa un token personal (Settings, Developer settings, Personal access tokens) o inicia sesión con el asistente de VS Code o con GitHub Desktop |
| `remote origin already exists` | `git remote set-url origin https://github.com/TU-USUARIO/repo.git` |
| `rejected, non-fast-forward` | El remoto tiene commits que no tienes. Haz `git pull --rebase origin main` y luego `git push` |
| Olvidaste un archivo en el último commit | `git add archivo` y `git commit --amend --no-edit`, solo si aún no hiciste push |
| Mensaje con una errata en el último commit | `git commit --amend -m "Mensaje corregido"`, solo si aún no hiciste push |
| Subiste la carpeta `out/` por error | `git rm -r --cached out` y commit |
| Commits con otro nombre | Revisa `git config user.name` y `git config user.email` |

## Si trabajas en VS Code

El panel de Control de código fuente (Ctrl+Shift+G) permite escribir el mensaje, hacer commit y sincronizar. Revisa siempre la lista de archivos antes de confirmar.

## Antes de entregar

- [ ] `git status` limpio.
- [ ] `git log --oneline` muestra una historia lógica.
- [ ] Todo está en GitHub (abre el repositorio en el navegador y comprueba).
- [ ] El README tiene instrucciones de compilación y ejecución.
- [ ] El enlace del repositorio está en el informe.
