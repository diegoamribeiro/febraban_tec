import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.cap.techsurvey.R

class WebPageDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_page_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView: WebView = view.findViewById(R.id.webview)
        webView.webViewClient = WebViewClient() // Use a WebViewClient to handle loading new pages in the WebView itself
        webView.loadUrl("https://www.capgemini.com/br-pt/privacy-policy/")

        // Enable JavaScript (if the web page requires it)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }

    override fun onStart() {
        super.onStart()

        // Make dialogFragment corners rounded
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
    }
}
