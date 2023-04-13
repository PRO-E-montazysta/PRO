package com.example.e_montazysta.ui.tools

import com.example.e_montazysta.ui.list.ListAdapter
import com.example.e_montazysta.ui.list.ListAdapterDelegate
import com.example.e_montazysta.ui.list.ListItem

class ToolsListAdapter : ListAdapter<ListItem>() {
    init {
        delegateManager.addDelegates(ToolDelegate() as ListAdapterDelegate<ListItem>)
    }
}