$programs = @("main.exe")
$threads = @(2, 4, 8, 16)
$runs = 10

foreach ($prog in $programs) {
    foreach ($t in $threads) {
        Write-Host "Rulăm $prog cu $t thread-uri, $runs rulari"
        & .\scriptC.ps1 $prog $t $runs
        Write-Host "`n"
    }
}