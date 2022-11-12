//
//  DestinationTableViewCell.swift
//  CatchCha
//
//  Created by 정태우 on 2022/11/13.
//

import UIKit

class DestinationTableViewCell: UITableViewCell {

    // MARK: - Properties
    @IBOutlet weak var placeLabel: UILabel!
    @IBOutlet weak var distanceLabel: UILabel!
    @IBOutlet weak var addressLabel: UILabel!
    
    
    // MARK: - Life Cycles
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
