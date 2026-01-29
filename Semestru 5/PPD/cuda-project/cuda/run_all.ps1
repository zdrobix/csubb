# Script complet de testare CUDA pentru Laboratorul 2
# Rulează teste pentru diferite dimensiuni de matrice

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Testare implementare CUDA - Lab 2" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Verificare NVCC
Write-Host "Verificare compilator NVCC..." -ForegroundColor Yellow
try {
    nvcc --version | Select-Object -First 1
    Write-Host "NVCC gasit!" -ForegroundColor Green
} catch {
    Write-Host "EROARE: NVCC nu este instalat sau nu este in PATH!" -ForegroundColor Red
    exit 1
}

Write-Host ""

# Compilare
Write-Host "Compilare main.cu cu NVCC..." -ForegroundColor Yellow
nvcc -o main.exe main.cu 2>&1

if ($LASTEXITCODE -ne 0) {
    Write-Host "EROARE la compilare!" -ForegroundColor Red
    exit 1
}

Write-Host "Compilare reusita!" -ForegroundColor Green
Write-Host ""

# Teste conform cerintei
$testCases = @(
    @{N=10; M=10; Runs=10},
    @{N=1000; M=1000; Runs=10},
    @{N=10000; M=10000; Runs=10}
)

# Creare/reset fisier CSV
if (Test-Path outCUDA.csv) {
    Remove-Item outCUDA.csv
}
New-Item outCUDA.csv -ItemType File
Set-Content outCUDA.csv 'Dimensiune,Timp mediu (ns),Timp mediu (ms)'

foreach ($test in $testCases) {
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Test: Matrice $($test.N)x$($test.M)" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    
    # Generare date de test
    Write-Host "Generare date de test..." -ForegroundColor Yellow
    python generare.py $test.N $test.M 3
    
    # Rulare teste
    $suma = 0
    for ($i = 0; $i -lt $test.Runs; $i++) {
        Write-Host "  Rulare $($i+1)/$($test.Runs)..." -ForegroundColor Gray
        $output = (cmd /c .\main.exe 2>&1)
        $timp = $output[$output.length-1]
        Write-Host "    Timp: $timp ns" -ForegroundColor Gray
        $suma += [long]$timp
    }
    
    $media_ns = $suma / $test.Runs
    $media_ms = $media_ns / 1000000
    
    Write-Host ""
    Write-Host "Timp mediu: $media_ns ns ($media_ms ms)" -ForegroundColor Green
    Write-Host ""
    
    # Salvare in CSV
    Add-Content outCUDA.csv "$($test.N)x$($test.M),$media_ns,$media_ms"
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Testare finalizata!" -ForegroundColor Green
Write-Host "  Rezultate salvate in outCUDA.csv" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
