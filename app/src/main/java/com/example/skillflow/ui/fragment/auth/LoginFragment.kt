package com.example.skillflow.ui.fragment.auth

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.skillflow.R
import com.example.skillflow.core.utils.hideKeyboard
import com.example.skillflow.databinding.DialogErrorBinding
import com.example.skillflow.databinding.DialogLoadingBinding
import com.example.skillflow.databinding.FragmentLoginBinding
import com.example.skillflow.ui.viewModel.LoginUIState
import com.example.skillflow.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    companion object {
        private const val VK_URL = "https://vk.com/"
        private const val OK_URL = "https://ok.ru/"
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    private var loadingDialog: AlertDialog? = null
    private var errorDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFilter()
        setupTextWatchers()
        observeViewModel()
        setupButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        loadingDialog?.dismiss()
        errorDialog?.dismiss()
        _binding = null
    }

    private fun setupFilter() = with(binding) {
        val filter = InputFilter { source, _, _, _, _, _ ->
            if (source.toString().matches(Regex("[а-яА-ЯёЁ]"))) "" else null
        }
        edtEmail.filters = arrayOf(filter)
    }

    private fun setupTextWatchers() = with(binding) {
        edtEmail.doAfterTextChanged { text ->
            viewModel.onEmailChanged(text.toString().trim())
        }

        edtPassword.doAfterTextChanged { text ->
            viewModel.onPasswordChanged(text.toString())
        }
    }

    private fun observeViewModel() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoginEnabled.collect { isEnabled ->
                    btnEntrance.isEnabled = isEnabled
                    btnEntrance.alpha = if (isEnabled) 1.0f else 0.5f
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is LoginUIState.Standard -> {
                            hideLoadingDialog()
                            hideErrorDialog()
                        }
                        is LoginUIState.Loading -> {
                            showLoadingDialog()
                        }
                        is LoginUIState.Success -> {
                            hideLoadingDialog()
                            hideErrorDialog()
                        }
                        is LoginUIState.Error -> {
                            hideLoadingDialog()
                            showErrorDialog(state.message)
                        }
                    }
                }
            }
        }
    }

    private fun setupButtons() = with(binding) {
        btnEntrance.setOnClickListener {
            hideKeyboard()
            viewModel.login {
                findNavController().navigate(R.id.action_login_to_main)
            }
        }

        imLogoVK.setOnClickListener { openBrowser(VK_URL) }
        imLogoOK.setOnClickListener { openBrowser(OK_URL) }

        tvRegistration.isEnabled = false
        tvForgotPassword.isEnabled = false
        tvRegistration.alpha = 0.5f
        tvForgotPassword.alpha = 0.5f
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun showLoadingDialog() {
        if (loadingDialog?.isShowing == true) return

        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater)
        loadingDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showErrorDialog(message: String) {
        val dialogBinding = DialogErrorBinding.inflate(layoutInflater)
        errorDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.tvError.text = message

        dialogBinding.btnCancelDialog.setOnClickListener {
            errorDialog?.dismiss()
        }

        errorDialog?.show()
    }

    private fun hideErrorDialog() {
        errorDialog?.dismiss()
        errorDialog = null
    }
}