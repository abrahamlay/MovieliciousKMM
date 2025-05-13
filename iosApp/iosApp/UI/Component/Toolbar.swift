//
//  Toolbar.swift
//  iosApp
//
//  Created by Abraham Lay on 13/05/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct Toolbar: View {
    var body: some View {
        HStack {
            Button(action: {
                print("Menu Clicked")
            }) {
                Image(systemName: "line.horizontal.3")
            }
            Spacer()
            Text("Movielicious")
                .font(.headline)
            Spacer()
            Button(action: {
                print("Search Clicked")
            }) {
                Image(systemName: "magnifyingglass")
            }
        }
        .padding()
        .background(Color(.systemBackground))
    }
}
