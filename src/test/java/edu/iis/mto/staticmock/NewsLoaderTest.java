package edu.iis.mto.staticmock;

import static org.powermock.api.mockito.PowerMockito.*;

import org.junit.Before;
import org.mockito.Mockito;

import edu.iis.mto.staticmock.reader.NewsReader;

public class NewsLoaderTest {

    private ConfigurationLoader configurationLoader;
	
	@Before
	public void start() {
		configurationLoader = mock(ConfigurationLoader.class);
		when(configurationLoader.loadConfiguration()).thenReturn(new Configuration());
		mockStatic(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
		
		NewsReader newsReader = mock(NewsReader.class);
		when(newsReader.read()).thenReturn(new IncomingNews());
		
		mockStatic(NewsReaderFactory.class);
		when(NewsReaderFactory.getReader(Mockito.anyString())).thenReturn(newsReader);
	}

}
