name: Build, Test
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v1
    - name: Build
      uses: vgaidarji/android-github-actions/build@v1.0.1
      env:
        FABRIC_API_KEY: ${{ secrets.FABRIC_API_KEY }}
        FABRIC_API_SECRET: ${{ secrets.FABRIC_API_SECRET }}
      with:
        args: "sdkmanager \"platforms;android-26\" && ./gradlew assembleDebug -PpreDexEnable=false"
    - name: Check
      uses: vgaidarji/android-github-actions/build@v1.0.1
      env:
        FABRIC_API_KEY: ${{ secrets.FABRIC_API_KEY }}
        FABRIC_API_SECRET: ${{ secrets.FABRIC_API_SECRET }}
      with:
        args: "./gradlew testDebug"