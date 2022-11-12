//
//  DestinationViewController.swift
//  CatchCha
//
//  Created by 정태우 on 2022/11/13.
//

import UIKit

class DestinationViewController: UIViewController {

    // MARK: - Properties
    @IBOutlet weak var tableView: UITableView!
    
    let destinationDataModel : [DestinationDataModel] = [
        DestinationDataModel(place: "제물포역 1번 출구", distance: "1km", address: "인천광역시 미추홀구 경인로 119"),
        DestinationDataModel(place: "제물포역 2번 출구", distance: "2km", address: "인천광역시 미추홀구 경인로 129"),
        DestinationDataModel(place: "제물포역 3번 출구", distance: "3km", address: "인천광역시 미추홀구 경인로 139"),
        DestinationDataModel(place: "제물포역 4번 출구", distance: "4km", address: "인천광역시 미추홀구 경인로 149"),
        DestinationDataModel(place: "제물포역 5번 출구", distance: "5km", address: "인천광역시 미추홀구 경인로 159")
    ]
    
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.delegate = self
        tableView.dataSource = self
        
        registerXib()
    }
    
    func registerXib() { // xib 등록
        let nibName = UINib(nibName: "DestinationTableViewCell", bundle: nil)
        tableView.register(nibName, forCellReuseIdentifier: "DestinationTableViewCell")
    }
    
    @IBAction func backButton(_ sender: Any) {
        self.presentingViewController?.dismiss(animated: true)
    }
    
}

extension DestinationViewController : UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return destinationDataModel.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "DestinationTableViewCell", for: indexPath) as? DestinationTableViewCell else { return UITableViewCell() }
        
        cell.placeLabel.text = destinationDataModel[indexPath.row].place
        cell.distanceLabel.text = destinationDataModel[indexPath.row].distance
        cell.addressLabel.text = destinationDataModel[indexPath.row].address
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: "cellDidTap", sender: nil)
        
    }

}


struct DestinationDataModel {
    let place : String
    let distance : String
    let address : String
}
