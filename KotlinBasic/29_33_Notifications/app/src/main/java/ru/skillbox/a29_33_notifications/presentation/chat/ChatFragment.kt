package ru.skillbox.a29_33_notifications.presentation.chat

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.a29_33_notifications.R
import ru.skillbox.a29_33_notifications.data.Message
import ru.skillbox.a29_33_notifications.databinding.FragmentChatBinding
import ru.skillbox.a29_33_notifications.utils.autoCleared
import timber.log.Timber


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding: FragmentChatBinding by viewBinding(FragmentChatBinding::bind)
    private var messageAdapter: MessageAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()

        //  https://stackoverflow.com/a/8063533/3780442
        binding.typeMessage.setOnEditorActionListener(
            OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action === KeyEvent.ACTION_DOWN && event.keyCode === KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed) {
                        // the user is done typing.
                        Timber.d("Typed message ${v.text}")
                        val oldList = messageAdapter.items
                        val size = messageAdapter.items.size
                        val myMessage = Message.MyMessage(size + 1L, v.text.toString())
                        var newList = oldList + myMessage
//                        newList = oldList.add(myMessage) //didn't work
                        messageAdapter.items = newList
                        binding.typeMessage.text.clear()
                        return@OnEditorActionListener true // consume.
                    }
                }
                false // pass on to other listeners.
            }
        )
    }

    private fun initList() {
        messageAdapter = MessageAdapter()
        with(binding.chatRecyclerView) {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}