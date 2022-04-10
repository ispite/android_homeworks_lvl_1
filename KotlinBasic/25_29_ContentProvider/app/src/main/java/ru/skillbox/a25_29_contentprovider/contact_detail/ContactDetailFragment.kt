package ru.skillbox.a25_29_contentprovider.contact_detail

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contact_detail.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.utils.toast

class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail) {

    private val args: ContactDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<ContactDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initAdapter()
        removeContactFAB.setOnClickListener {
            deleteContactWithPermissionCheck()
        }
    }

    private fun bindViewModel() {
        viewModel.getContact(args.contactId)
        viewModel.contactName.observe(viewLifecycleOwner) {
            contactNameDetailTextView.text = it
        }
        viewModel.deleteState.observe(viewLifecycleOwner) {
            if (it == 1) {
                findNavController().popBackStack()
            }
        }
    }

    private fun initAdapter() {
        val contactInfoAdapter = ContactInfoAdapter()
        viewModel.getPhonesAndEmails(args.contactId)
        with(contactInfoListRecyclerView) {
            adapter = contactInfoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.contactInfoList.observe(viewLifecycleOwner) {
            contactInfoAdapter.items = it
        }
    }

    private fun deleteContactWithPermissionCheck() {
        constructPermissionsRequest(
            Manifest.permission.WRITE_CONTACTS,
            onShowRationale = ::onContactPermissionShowRationale,
            onPermissionDenied = ::onContactPermissionDenied,
            onNeverAskAgain = ::onContactPermissionNeverAskAgain
        ) {
            viewModel.removeContact(
                args.contactId
            )
        }.launch()
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionDenied() {
        toast(R.string.add_contact_permission_denied)
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.add_contact_permission_never_ask_again)
    }
}