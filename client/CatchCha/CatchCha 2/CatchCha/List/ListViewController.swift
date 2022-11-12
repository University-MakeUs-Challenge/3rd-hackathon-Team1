import UIKit

class ListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.availPatiesArr.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let partyCell = tableView.dequeueReusableCell(withIdentifier: "AvailablePartyTableViewCell", for: indexPath) as? AvailablePartyTableViewCell else { return UITableViewCell() }
        
        partyCell.moveRouteLabel.text = availPatiesArr[indexPath.row].originLocation + " - " + availPatiesArr[indexPath.row].destinationLocation
        partyCell.restEndTimeLabel.text = availPatiesArr[indexPath.row].restEndTime + " 만료 예정"

        
        
        return partyCell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: "tapCell", sender: nil)
    }
    

    
    @IBOutlet weak var setPinPullBtn: UIButton!
    @IBOutlet weak var availablePartyTable: UITableView!
    
    @IBOutlet weak var pinItem1: UICommand!
    @IBOutlet weak var pinItem2: UICommand!
    @IBOutlet weak var pinItem3: UICommand!
    
    
    
    
    func setPinMenu() {
        lazy var menuItems: [UIAction] = {
            return [
                UIAction(title: self.availablePinArr[0].name, image: nil, handler: { _ in }),
                UIAction(title: self.availablePinArr[1].name, image: nil, handler: { _ in }),
                UIAction(title: self.availablePinArr[0].name, image: nil, handler: { _ in }),
            ]
        }()
        
        lazy var menu: UIMenu = {
            return UIMenu(title: "", options: [], children: menuItems)
        }()
        
        self.setPinPullBtn.menu = menu
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setPinMenu()
        availablePartyTable.delegate = self
        availablePartyTable.dataSource = self
    }

    // test data
    let availablePinArr: [AvailablePinDataModel] = [
        AvailablePinDataModel(name: "인하대 정문", availablePartyCount: 2),
        AvailablePinDataModel(name: "인하대 후문", availablePartyCount: 3),
        AvailablePinDataModel(name: "인하대 학생회관", availablePartyCount: 1)
    ]
    
    let availPatiesArr: [AvailablePartyDataModel] = [
        AvailablePartyDataModel(originLocation: "인하대 후문", destinationLocation: "제물포역 1번 출구", restEndTime: "8시 43분", matchedUserNum: 2),
        AvailablePartyDataModel(originLocation: "인하대 정문", destinationLocation: "제물포역 1번 출구", restEndTime: "8시 55분", matchedUserNum: 2),
        AvailablePartyDataModel(originLocation: "인하대 정문", destinationLocation: "도화역 5번 출구", restEndTime: "8시 59분", matchedUserNum: 3),
        AvailablePartyDataModel(originLocation: "인하대 후문", destinationLocation: "도화역 5번 출구", restEndTime: "9시 8분", matchedUserNum: 3),
        AvailablePartyDataModel(originLocation: "인하대 학생회관", destinationLocation: "제물포역 8번 출구", restEndTime: "9시 10분", matchedUserNum: 1)
    ]
}

struct AvailablePinDataModel {
    let name: String
    let availablePartyCount: Int
}

struct AvailablePartyDataModel {
    let originLocation: String
    let destinationLocation: String
    let restEndTime: String
    let matchedUserNum: Int
}
