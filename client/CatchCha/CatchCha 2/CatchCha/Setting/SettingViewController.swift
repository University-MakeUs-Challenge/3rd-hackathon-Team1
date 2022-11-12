//
//  SettingViewController.swift
//  CatchCha
//
//  Created by 정태우 on 2022/11/12.
//

import UIKit

class SettingViewController: UIViewController {

    @IBOutlet weak var taxiView: UIView!
    override func viewDidLoad() {
        super.viewDidLoad()
        self.taxiView.layer.borderColor = UIColor.lightGray.cgColor
        self.taxiView.layer.borderWidth = 1
        self.taxiView.layer.cornerRadius = 10
    }
    


}
