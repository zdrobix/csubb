@echo off
REM Script de compilare CUDA
REM Foloseste Visual Studio 2019 Build Tools

echo ========================================
echo   Compilare CUDA - Laborator 2
echo ========================================
echo.

echo Compilare main.cu cu NVCC...
call "C:\Program Files (x86)\Microsoft Visual Studio\2019\BuildTools\VC\Auxiliary\Build\vcvarsall.bat" x64 >nul 2>&1
nvcc -o main.exe main.cu

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilare reusita!
    echo Executabil: main.exe
    echo.
    echo Pentru a rula:
    echo   .\main.exe
    echo.
) else (
    echo.
    echo Eroare la compilare!
    echo.
    exit /b 1
)
