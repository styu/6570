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
                
                if (pei.location.contains("#")) {
                	v = vi.inflate(R.layout.printer_list_item_entry_long, null);
                	final TextView printerCommonLocation = (TextView) v
                			.findViewById(R.id.list_item_printer_common_location);
                	if (printerCommonLocation != null) {
                		String commonLocation = pei.location.split("#")[1];
                		printerCommonLocation.setText(commonLocation);
                	}
                }
                else {
                	v = vi.inflate(R.layout.printer_list_item_entry, null);
                }
                //final TextView text = (TextView) v.findViewById(R.id.list_item_text);
                
                final TextView printerName = (TextView) v
                        .findViewById(R.id.list_item_printer_name);
                final TextView printerLocation = (TextView) v
                        .findViewById(R.id.list_item_printer_location);
                final TextView printerStatus = (TextView) v
                		.findViewById(R.id.list_item_printer_status);
                
                
                if (printerName != null)
                	printerName.setText(pei.printerName);
                if (printerLocation != null) {
                	if (pei.location.contains("#")) {
                		printerLocation.setText(pei.location.split("#")[0]);
                	}
                	else {
                		printerLocation.setText(pei.location);
                	}
                }
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
            } 
        }
        return v;
    }

}
