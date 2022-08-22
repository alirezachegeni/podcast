package contagiouscode.mirsengar.podcast.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.util.Log
import contagiouscode.mirsengar.podcast.R
import contagiouscode.mirsengar.podcast.domain.model.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PodcastDetailViewModel @Inject constructor() : ViewModel() {
     fun sharePodcastEpisode(context : Context , episode : Episode) {
          val text = context.getString(
                    R.string.share_podcast_content ,
                    episode.titleOriginal ,
                    episode.listennotesURL
          )
          val sendIntent : Intent = Intent().apply {
               action = Intent.ACTION_SEND
               putExtra(Intent.EXTRA_TITLE , episode.titleOriginal)
               putExtra(Intent.EXTRA_TEXT , text)
               type = "text/plain"
          }
          val shareIntent = Intent.createChooser(sendIntent , null)
          Log.i("Err",shareIntent.toString())
          context.startActivity(shareIntent)
     }
     
     fun openListenNotesURL(context : Context , episode : Episode) {
          val webIntent = Intent(Intent.ACTION_VIEW , Uri.parse(episode.link))
          context.startActivity(webIntent)
     }
}