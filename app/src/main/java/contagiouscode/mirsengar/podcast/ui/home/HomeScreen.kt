package contagiouscode.mirsengar.podcast.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import contagiouscode.mirsengar.podcast.domain.model.Episode
import contagiouscode.mirsengar.podcast.ui.common.PreviewContent
import contagiouscode.mirsengar.podcast.ui.common.StaggeredVerticalGrid
import contagiouscode.mirsengar.podcast.ui.common.ViewModelProvider
import contagiouscode.mirsengar.podcast.ui.navigation.Destination
import contagiouscode.mirsengar.podcast.ui.navigation.Navigator
import contagiouscode.mirsengar.podcast.util.Resource
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun HomeScreen() {
     val scrollState = rememberLazyListState()
     val navController = Navigator.current
     val podcastSearchViewModel = ViewModelProvider.podcastSearch
     val podcastSearch = podcastSearchViewModel.podcastSearch
     Surface {
          LazyColumn(state = scrollState) {
               item {
                    LargeTitle()
               }
               when (podcastSearch) {
                    is Resource.Error   -> {
                         item {
                              ErrorView(text = podcastSearch.failure.translate()) {
                                   podcastSearchViewModel.searchPodcasts()
                              }
                         }
                    }
                    Resource.Loading    -> {
                         item {
                              LoadingPlaceholder()
                         }
                    }
                    is Resource.Success -> {
                         item {
                              StaggeredVerticalGrid(
                                        crossAxisCount = 2 ,
                                        spacing = 16.dp ,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                              ) {
                                   podcastSearch.data.results.forEach { podcast ->
                                        PodcastView(
                                                  podcast = podcast ,
                                                  modifier = Modifier.padding(bottom = 16.dp)
                                        ) {
                                             openPodcastDetail(navController , podcast)
                                        }
                                   }
                              }
                         }
                    }
               }
               item {
                    Box(
                              modifier = Modifier
                                        .navigationBarsPadding()
                                        .padding(bottom = 32.dp)
                                        .padding(bottom = if (ViewModelProvider.podcastPlayer.currentPlayingEpisode.value != null) 64.dp else 0.dp)
                    )
               }
          }
     }
}

private fun openPodcastDetail(
          navController : NavHostController ,
          podcast : Episode ,
) {
     navController.navigate(Destination.podcast(podcast.id)) { }
}

@Composable
@Preview(name = "Home")
fun HomeScreenPreview() {
     PreviewContent {
          HomeScreen()
     }
}

@Composable
@Preview(name = "Home (Dark)")
fun HomeScreenDarkPreview() {
     PreviewContent(darkTheme = true) {
          HomeScreen()
     }
}