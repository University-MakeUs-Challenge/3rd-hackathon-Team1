//
//  PartyloadingViewController.swift
//  CatchCha
//
//  Created by juhee on 2022/11/12.
//

import UIKit

class PartyloadingViewController: UIViewController {

    var people: Int?
    var minute: Int?
    
    
    @IBOutlet weak var progressView: UIProgressView!
    override func viewDidLoad() {
        super.viewDidLoad()
        self.progressView.progress = 0.0
        TimerProgress()
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 3) {
            guard let partyViewController = self.storyboard?.instantiateViewController(withIdentifier: "PartyViewController") as? PartyViewController else { return }
            partyViewController.modalPresentationStyle = .fullScreen
            partyViewController.people = self.people
            partyViewController.minute = self.minute
            self.present(partyViewController, animated: true)
          }
    }
    var timer1 = Timer()
    var timer2 = Timer()
    var secondsPassed = 0
    let time = 300
    func TimerProgress() {
        timer1 = Timer.scheduledTimer(withTimeInterval: 0.01, repeats: true) { (Timer) in
            if self.secondsPassed < self.time + 1 {
                self.secondsPassed += 1
                let percent = Float(self.secondsPassed) / Float(self.time)
                self.progressView.setProgress(percent, animated: true)
            }
        }
    }
    
}
