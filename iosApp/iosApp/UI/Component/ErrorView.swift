//
//  ErrorView.swift
//  iosApp
//
//  Created by Abraham Lay on 28/05/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct ErrorView: View {
    let error: String
    let retryAction: (() -> Void)?
    
    var body: some View {
        VStack(spacing: 16) {
            Image(systemName: "exclamationmark.triangle")
                .font(.system(size: 48))
                .foregroundColor(.red)
            
            Text("Error")
                .font(.title)
            
            Text(error)
                .font(.subheadline)
                .multilineTextAlignment(.center)
            
            if let retryAction = retryAction {
                Button("Retry", action: retryAction)
                    .buttonStyle(.borderedProminent)
                    .padding(.top, 8)
            }
        }
        .padding()
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(.systemBackground))
    }
}
