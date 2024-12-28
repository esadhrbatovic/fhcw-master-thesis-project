$ErrorActionPreference = "Stop"

$rootDir = Get-Location
$targetDir = Join-Path -Path $rootDir -ChildPath "..\service-specs"
$folders = Get-ChildItem -Directory | Where-Object { $_.Name -like "eh-ma-quarkus*" }

foreach ($folder in $folders) {
    $metaInfPath = Join-Path -Path $folder.FullName -ChildPath "src\main\resources\META-INF"
    $htmlFilePath = Join-Path -Path $metaInfPath -ChildPath "openapi.html"
    $yamlFilePath = Join-Path -Path $metaInfPath -ChildPath "openapi.yaml"

    if (Test-Path $htmlFilePath) {
        Write-Host "Found: $htmlFilePath"

        $serviceName = $folder.Name.Replace("eh-ma-quarkus-", "")
        $serviceTargetDir = Join-Path -Path $targetDir -ChildPath $serviceName

        if (-not (Test-Path $serviceTargetDir)) {
            Write-Host "Creating target directory: $serviceTargetDir"
            New-Item -ItemType Directory -Path $serviceTargetDir
        }

        $htmlDestinationPath = Join-Path -Path $serviceTargetDir -ChildPath "openapi.html"
        Write-Host "Copying $htmlFilePath to $htmlDestinationPath"
        Copy-Item -Path $htmlFilePath -Destination $htmlDestinationPath -Force

        if (Test-Path $yamlFilePath) {
            $yamlDestinationPath = Join-Path -Path $serviceTargetDir -ChildPath "openapi.yaml"
            Write-Host "Copying $yamlFilePath to $yamlDestinationPath"
            Copy-Item -Path $yamlFilePath -Destination $yamlDestinationPath -Force
        } else {
            Write-Warning "YAML file not found in $metaInfPath"
        }
    } else {
        Write-Warning "HTML file not found in $metaInfPath"
    }
}

Write-Host "Script completed."
