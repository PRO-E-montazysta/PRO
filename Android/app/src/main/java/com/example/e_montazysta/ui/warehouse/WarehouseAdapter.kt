import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO

class WarehouseListAdapter(
    context: Context,
    private val warehouses: List<WarehouseFilterDAO>
) : ArrayAdapter<WarehouseFilterDAO>(context, 0, warehouses), ListAdapter {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val warehouse = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_single_choice, parent, false)
        }

        val textView = view!!.findViewById<TextView>(android.R.id.text1)
        textView.text = warehouse?.name

        return view
    }
}