import SwiftUI
import Shared
import Compose

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            MainViewControllerRepresentable()
                .ignoresSafeArea()
		}
	}
}

struct MainViewControllerRepresentable: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return SharedKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}