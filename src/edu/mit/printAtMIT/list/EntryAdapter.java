package edu.mit.printAtMIT.list;

import java.util.ArrayList;

import edu.mit.printAtMIT.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EntryAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater vi;

    public EntryAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final Item i = items.get(position);
        if (i != null) {
            if (i.isSection()) {
                SectionItem si = (SectionItem) i;
                v = vi.inflate(R.layout.list_item_section, null);

                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);

                final TextView sectionView = (TextView) v
                        .findViewById(R.id.list_item_section_text);
                sectionView.setText(si.getTitle());
            } else if (i.isPrinterEntry()) {
                PrinterEntryItem pei = (PrinterEntryItem) i;
                //TODO: make new layout for printer entries that show printer name, printer location, and printer status
                v = vi.inflate(R.layout.printer_list_item_entry, null);
                //final TextView text = (TextView) v.findViewById(R.id.list_item_text);
                
                final TextView printerName = (TextView) v
                        .findViewById(R.id.list_item_printer_name);
                final TextView printerLocation = (TextView) v
                        .findViewById(R.id.list_item_printer_location);
                final TextView printerStatus = (TextView) v
                		.findViewById(R.id.list_item_printer_status);
                
                if (printerName != null)
                	printerName.setText(pei.printerName);
                if (printerLocation != null)
                	printerLocation.setText(pei.location);
                if (printerStatus != null) {
                	String status = pei.getStatusString();
                	printerStatus.setText(status);
                	ImageView circle = (ImageView) v.findViewById(R.id.status_dot);
                	
                	if (status.equals(PrinterEntryItem.READY)) {
                		circle.setImageResource(R.drawable.green_dot);
                	}
                	else if (status.equals(PrinterEntryItem.BUSY)) {
                		circle.setImageResource(R.drawable.yellow_dot);
                	}
                	else if (status.equals(PrinterEntryItem.ERROR)) {
                		circle.setImageResource(R.drawable.red_dot);
                	}
                	else {
                		circle.setImageResource(R.drawable.grey_dot);
                	}
                }
                
                /*if (text != null) {
//                    text.setText(pei.printerName + "\t\t" + pei.location + "\t\t" + new Integer(pei.status).toString());
                    text.setText(pei.printerName + "\t\t" + pei.location + "\t\t" + pei.getStatusString());

                }*/
            } else if (!i.isButton()) {
                EntryItem ei = (EntryItem) i;
                v = vi.inflate(R.layout.list_item_entry, null);
                final TextView title = (TextView) v
                        .findViewById(R.id.list_item_entry_title);
                final TextView subtitle = (TextView) v
                        .findViewById(R.id.list_item_entry_summary);

                if (title != null)
                    title.setText(ei.title);
                if (subtitle != null)
                    subtitle.setText(ei.subtitle);
            } else {
                ButtonItem bi = (ButtonItem) i;

                v = vi.inflate(R.layout.print_options, null);
                TextView printButton = (TextView) v
                        .findViewById(R.id.print_button);

                if (printButton != null) {
                    printButton.setText(bi.title);
                }
            }
        }
        return v;
    }

}
