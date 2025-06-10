Write-Host "📁 Scansione file .java in src/dto..." -ForegroundColor Cyan

$srcPath = "src\dto"
$javaFiles = Get-ChildItem -Path $srcPath -Filter *.java -Recurse

if (-not $javaFiles) {
    Write-Host "⚠️  Nessun file .java trovato in dto." -ForegroundColor Yellow
    exit
}

$errore = $false

foreach ($file in $javaFiles) {
    $content = Get-Content $file -Encoding UTF8
    foreach ($line in $content) {
        if ($line -match '[^\x00-\x7F]') {
            Write-Host "🚨 Caratteri non ASCII trovati in: $($file.Name)" -ForegroundColor Red
            $errore = $true
            break
        }
    }
}

if ($errore) {
    Write-Host "`n❌ Fermato: almeno un file contiene caratteri non validi." -ForegroundColor Red
    Write-Host "💡 Consiglio: apri i file in VS Code e rimuovi spazi invisibili o lettere accentate strane."
    exit
}

Write-Host "`n✅ Tutti i file .java in dto sono puliti!" -ForegroundColor Green
Write-Host "🛠️  Generazione della Javadoc in corso..."

javadoc -encoding UTF-8 -charset UTF-8 -private -d javadoc-dto -sourcepath src dto/*.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "`n✅ Javadoc generata correttamente in 'javadoc-dto'!" -ForegroundColor Green
    Start-Process javadoc-dto\index.html
} else {
    Write-Host "`n⚠️  Errore durante la generazione della Javadoc." -ForegroundColor Yellow
}