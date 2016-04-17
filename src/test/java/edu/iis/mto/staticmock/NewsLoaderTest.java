package edu.iis.mto.staticmock;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.reader.NewsReader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})
public class NewsLoaderTest {

    private ConfigurationLoader configurationLoader;
	private NewsReader newsReader;
	
	@Before
	public void start() {
		configurationLoader = mock(ConfigurationLoader.class);
		when(configurationLoader.loadConfiguration()).thenReturn(new Configuration());
		mockStatic(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
		
		newsReader = mock(NewsReader.class);
//		when(newsReader.read()).thenReturn(new IncomingNews());
		
		mockStatic(NewsReaderFactory.class);
		when(NewsReaderFactory.getReader(Mockito.anyString())).thenReturn(newsReader);
	}

	@Test
	public void publicNewsTest () {
        IncomingNews incomingNews = new IncomingNews();
        incomingNews.add(new IncomingInfo("Subsciption type A.", SubsciptionType.A));
        incomingNews.add(new IncomingInfo("Subsciption type C.", SubsciptionType.C));
        incomingNews.add(new IncomingInfo("Subsciption non type.", SubsciptionType.NONE));

        when(newsReader.read()).thenReturn(incomingNews);
        NewsLoader newsLoader = new NewsLoader();
        PublishableNews publishableNews = newsLoader.loadNews();
        assertThat(publishableNews.getPublicContent().size(), is(1));
	}
	
	@Test
	public void subscriberNewsTest () {
        IncomingNews incomingNews = new IncomingNews();
        incomingNews.add(new IncomingInfo("Subsciption type A.", SubsciptionType.A));
        incomingNews.add(new IncomingInfo("Subsciption type C.", SubsciptionType.C));
        incomingNews.add(new IncomingInfo("Subsciption non type.", SubsciptionType.NONE));


        when(newsReader.read()).thenReturn(incomingNews);
        NewsLoader newsLoader = new NewsLoader();
        PublishableNews publishableNews = newsLoader.loadNews();
        assertThat(publishableNews.getSubscribentContent().size(), is(2));
	}
}
