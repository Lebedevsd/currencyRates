workflow "Build, Test" {
  on = "push"
}

action "Build" {
  uses = "vgaidarji/android-github-actions/build@v1.0.0"
  args = "./gradlew assembleDebug"
}

action "Check" {
  needs = ["Build"]
  uses = "vgaidarji/android-github-actions/build@v1.0.0"
  args = "./gradlew testDebug"
}
