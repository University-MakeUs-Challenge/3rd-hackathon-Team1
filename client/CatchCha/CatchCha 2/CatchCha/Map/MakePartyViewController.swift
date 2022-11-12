//
//  MakePartyViewController.swift
//  CatchCha
//
//  Created by 정태우 on 2022/11/13.
//

import UIKit

class MakePartyViewController: UIViewController {
    @IBOutlet weak var backButton: UIBarButtonItem!
    
    
    
    @IBOutlet weak var progressView: UIProgressView!
    
  
    @IBOutlet weak var catchyButton: UIButton!
  
    @IBOutlet weak var minutesSegmentedControl: UISegmentedControl!
    
    @IBOutlet weak var personSegmentedControl: UISegmentedControl!

    
    var timer = Timer()
    var secondsPassed = 0
    let time = 300
    var minute: Int = 0
    var people: Int = 0
    override func viewDidLoad() {
        super.viewDidLoad()
        self.progressView.progress = 0.0
        TimerProgress()
        minutesSC()
        personSC()
    }
    func TimerProgress() {
        timer = Timer.scheduledTimer(withTimeInterval: 0.01, repeats: true) { (Timer) in
            if self.secondsPassed < self.time + 1 {
                self.secondsPassed += 1
                self.secondsPassed = self.secondsPassed % 301
                let percent = Float(self.secondsPassed) / Float(self.time)
                self.progressView.setProgress(percent, animated: true)
                if self.secondsPassed == 300 { self.progressView.progress = 0.0
                }
            }
        }
    }
    

    @IBAction func tapMinutesSegmentedControl(_ sender: UISegmentedControl) {
        minutesSC()
        self.minute = sender.selectedSegmentIndex
    }
    @objc func indexChanged(_ sender: UISegmentedControl) {
            // print("\(sender.selectedSegmentIndex)")
    }
    
    @IBAction func tapPersonSegmentedControl(_ sender: UISegmentedControl) {
        personSC()
        self.people = sender.selectedSegmentIndex
    }
    private func minutesSC() {
        self.minutesSegmentedControl.addTarget(self, action: #selector(indexChanged(_:)), for: .valueChanged)
        let titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        self.minutesSegmentedControl.setTitleTextAttributes(titleTextAttributes, for: .selected)
    }
    private func personSC() {
        self.personSegmentedControl.addTarget(self, action: #selector(indexChanged(_:)), for: .valueChanged)
        let titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        self.personSegmentedControl.setTitleTextAttributes(titleTextAttributes, for: .selected)
    }
    
    @IBAction func tapBackButton(_ sender: UIBarButtonItem) {
        self.presentingViewController?.dismiss(animated: true)
    }
    @IBAction func tapCatchyButton(_ sender: UIButton) {
        guard let partyLoadingViewController = self.storyboard?.instantiateViewController(withIdentifier: "PartyloadingViewController") as? PartyloadingViewController else { return }
        partyLoadingViewController.minute = self.minute
        partyLoadingViewController.people = self.people
        partyLoadingViewController.modalPresentationStyle = .fullScreen
        self.present(partyLoadingViewController, animated: true)
    }
    

}
