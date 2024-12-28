$ErrorActionPreference = "Stop"
$rootDir = Get-Location

$folders = Get-ChildItem -Directory | Where-Object { $_.Name -like "eh-ma-quarkus*" }

foreach ($folder in $folders) {
    $metaInfPath = Join-Path -Path $folder.FullName -ChildPath "src\main\resources\META-INF"

    if (Test-Path $metaInfPath) {
        Write-Host "Navigating to: $metaInfPath"

        Push-Location $metaInfPath

        try {
            Write-Host "Executing: generating html docs"
            npx @redocly/cli build-docs openapi.yaml -o ./openapi.html
        } catch {
        } finally {
            Pop-Location
        }
    } else {
        Write-Warning "META-INF directory does not exist in $folder.FullName"
    }
}

Write-Host "Script completed."
