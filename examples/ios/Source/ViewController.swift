import UIKit
import GLKit

class ReactView: UIView {
    let rootView: RCTRootView = ReactView.createReactRootView()

    static func createReactRootView() -> RCTRootView {
        let host: String
        if TARGET_OS_SIMULATOR == 1 {
            host = "localhost"
        } else {
            host = "192.168.2.1"
        }
        let url = NSURL(string: "http://\(host):8081/index.ios.bundle?platform=ios")
        return RCTRootView(bundleURL: url,
                           moduleName: "Example",
                           initialProperties: nil,
                           launchOptions: nil)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        loadReact()
    }
    
    func loadReact() {
        addSubview(rootView)
        rootView.frame = self.bounds
    }
}

class ViewController: UIViewController {
    override func loadView() {
        self.view = ReactView()
    }
}

