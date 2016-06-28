# Copied verbatim from React Native's podspec file.

require 'json'

package = JSON.parse(File.read(File.join(__dir__, '../package.json')))

Pod::Spec.new do |s|
  s.name                = "react-native-webview-bridge"
  s.version             = package['version']
  s.summary             = package['description'][0..42] + "..." # Summarize
  s.description         = package['description']
  s.homepage            = "https://github.com/alinz/react-native-webview-bridge"
  s.license             = package['license']
  s.author              = package['author']
  s.source              = { :git => "https://github.com/alinz/react-native-webview-bridge", :tag => "v#{s.version}" }
  s.requires_arc        = true
  s.platform            = :ios, "8.0"

  s.source_files = "*.{c,h,m,s}"
  # s.dependency 'Firebase', '~> 3.2'
end
