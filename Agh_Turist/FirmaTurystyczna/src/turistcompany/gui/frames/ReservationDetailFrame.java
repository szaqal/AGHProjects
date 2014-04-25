package turistcompany.gui.frames;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;

import turistcompany.db.DaoImpl;
import turistcompany.gui.MutableList;
import turistcompany.model.ReservedItem;

/**
 * Pokazuje szcegoly rezerwacji.
 * @author malczyk.pawel@gmail.com
 * @author gchmiel@lkn.pl
 *
 */
public class ReservationDetailFrame extends JFrame{
	
	/** Serial. */
	private static final long serialVersionUID = -8691461486304051910L;

	/**
	 * Zawiera informacje o szczegółach istniejacej rezrewacji.
	 * @param reservationId identyfikator rezerwacji dla ktorej szcególy wyświetlamy
	 */
	public ReservationDetailFrame(int reservationId) {
		setSize(200,300);
		setVisible(true);
		setLayout( new BorderLayout() );
		List<ReservedItem> reservedItems = new DaoImpl().reservedItems(reservationId);
		MutableList list = new MutableList();		
		
		String [] items = new String[reservedItems.size()];
		
		
		for ( int i=0;i<reservedItems.size();i++ ) {
			String value = String.format("%s - %s", reservedItems.get(i).getUniqueId(),reservedItems.get(i).getName());
			items[i] = value;
		}
		list.addList(items);
		
		add(list,BorderLayout.CENTER);
	}
	
	
}
