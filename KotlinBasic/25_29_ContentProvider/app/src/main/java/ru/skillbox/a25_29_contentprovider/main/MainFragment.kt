package ru.skillbox.a25_29_contentprovider.main

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.utils.autoCleared
import ru.skillbox.a25_29_contentprovider.utils.toast

class MainFragment : Fragment(R.layout.fragment_main), MainDialogFragment.MainDialogListener {

    private val viewModel by viewModels<MainViewModel>()
//    private val viewModelDialog by viewModels<MainDialogViewModel>()
    private var contactsAdapter: MainContactsListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()

        addContactFAB.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddContactFragment())
        }

        downloadFileFAB.setOnClickListener {
            MainDialogFragment()
                .show(childFragmentManager, "Dialog")
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToMainDialogFragment())
        }

        Handler(Looper.getMainLooper()).post{
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = {
                    viewModel.loadList() }
            )
                .launch()
        }

/*        viewModelDialog.downloadLink.observe(viewLifecycleOwner) {
            Log.d("MainFragment", "onViewCreated: $it")
        }*/

        viewModel.fileName.observe(viewLifecycleOwner) {
            Log.d("MainFragment", "onViewCreated: $it")
            if (it.isNotEmpty()) viewModel.shareFile()
        }

        viewModel.shareIntent.observe(viewLifecycleOwner) {
            startActivity(it)
        }
    }

    private fun initList() {
        contactsAdapter = MainContactsListAdapter { id ->
            val action = MainFragmentDirections.actionMainFragmentToContactDetailFragment(id)
            findNavController().navigate(action)
        }
        with(contactListRecyclerView) {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.contactsList.observe(viewLifecycleOwner) {
            contactsAdapter.setContactsList(it) }
    }

    private fun onContactPermissionDenied() {
        toast(R.string.contact_list_permission_denied)
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.contact_list_permission_never_ask_again)
    }

    override fun passDownloadLink(link: String) {
//        Log.d("MainFragment", "passDownloadLink: $link")
        val fileName = viewModel.downloadFile(link)
//        Log.d("MainFragment", "passDownloadLink: $fileName")
//        viewModel.shareFile()
    }
}