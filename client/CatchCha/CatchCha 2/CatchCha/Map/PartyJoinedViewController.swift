//
//  PartyJoinedViewController.swift
//  CatchCha
//
//  Created by juhee on 2022/11/13.
//

import UIKit

class PartyJoinedViewController: UIViewController {
    
    @IBOutlet weak var btnCatch: UIButton!
    @IBOutlet weak var viewCatcher: UIView!
    @IBOutlet weak var viewCatcher2: UIView!
    @IBOutlet weak var viewCatcher3: UIView!
    @IBOutlet weak var viewCatcher4: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.btnCatch.layer.cornerRadius = 15
        
        self.viewCatcher.layer.cornerRadius = 20
        self.viewCatcher.layer.shadowColor = UIColor.black.cgColor // 검정색 사용
        self.viewCatcher.layer.masksToBounds = false // view 벗어나면 잘라낼 것인지
        self.viewCatcher.layer.shadowOffset = CGSize(width: 0, height: 4) // 위치 조정
        self.viewCatcher.layer.shadowRadius = 3 // 반경
        self.viewCatcher.layer.shadowOpacity = 0.3 // alpha값
        
        self.viewCatcher2.layer.cornerRadius = 20
        self.viewCatcher2.layer.shadowColor = UIColor.black.cgColor // 검정색 사용
        self.viewCatcher2.layer.masksToBounds = false // view 벗어나면 잘라낼 것인지
        self.viewCatcher2.layer.shadowOffset = CGSize(width: 0, height: 4) // 위치 조정
        self.viewCatcher2.layer.shadowRadius = 3 // 반경
        self.viewCatcher2.layer.shadowOpacity = 0.3 // alpha값
        
        self.viewCatcher3.layer.cornerRadius = 20
        self.viewCatcher3.layer.borderWidth = 2
        self.viewCatcher3.layer.borderColor = UIColor(named: "buttonYellow")?.cgColor
        self.viewCatcher3.layer.shadowColor = UIColor.black.cgColor // 검정색 사용
        self.viewCatcher3.layer.masksToBounds = false // view 벗어나면 잘라낼 것인지
        self.viewCatcher3.layer.shadowOffset = CGSize(width: 0, height: 4) // 위치 조정
        self.viewCatcher3.layer.shadowRadius = 3 // 반경
        self.viewCatcher3.layer.shadowOpacity = 0.3 // alpha값
        
        self.viewCatcher4.layer.cornerRadius = 20
        self.viewCatcher4.layer.shadowColor = UIColor.black.cgColor // 검정색 사용
        self.viewCatcher4.layer.masksToBounds = false // view 벗어나면 잘라낼 것인지
        self.viewCatcher4.layer.shadowOffset = CGSize(width: 0, height: 4) // 위치 조정
        self.viewCatcher4.layer.shadowRadius = 3 // 반경
        self.viewCatcher4.layer.shadowOpacity = 0.3 // alpha값
        
    }

}
