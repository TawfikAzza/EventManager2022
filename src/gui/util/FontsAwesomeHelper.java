package gui.util;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.scene.text.Text;




//This class is a helper class for the FontAwesomeIconFactory class. It fetches all the icons for an easier experience.
public class FontsAwesomeHelper {
    public static Text getFontAwesomeIconForButtons(String iconID) throws RuntimeException {
        switch (iconID) {
            case "add":
                return FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS_CIRCLE);
            case "manage":
                return FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.CALENDAR);
            case "participants":
                return FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.USERS);
            case "sell":
                return FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.TICKET);
            case "scan":
                return FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.QRCODE);
            default:
                throw new RuntimeException("ICON NOT FOUND");
        }
    }
}
