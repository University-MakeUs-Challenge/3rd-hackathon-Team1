import UIKit

class AvailablePartyTableViewCell: UITableViewCell {

    @IBOutlet weak var moveRouteLabel: UILabel!
    @IBOutlet weak var restEndTimeLabel: UILabel!
    
    @IBOutlet weak var userImg1: UIImageView!
    @IBOutlet weak var userImg2: UIImageView!
    @IBOutlet weak var userImg3: UIImageView!
    @IBOutlet weak var userImg4: UIImageView!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

}
