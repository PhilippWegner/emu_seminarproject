package de.emuseminar.emu_seminarproject_client;

import java.text.SimpleDateFormat;
import java.util.List;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.*;
import org.jbehave.core.io.*;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.*;
import org.jbehave.core.steps.ParameterConverters.*;


import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.*;


public class JbehaveStories extends JUnitStories {
	public JbehaveStories(){ configuredEmbedder().embedderControls()
		.doGenerateViewAfterStories(true)
		.doIgnoreFailureInStories(true)
		.doIgnoreFailureInView(true)
		.useThreads(1);
	}
	
	@Override
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
		// Start from default ParameterConverters instance
		ParameterConverters parameterConverters = new ParameterConverters();
		// add custom converters
		parameterConverters.addConverters(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")));
		return new MostUsefulConfiguration()
				.useStoryLoader(new LoadFromClasspath(embeddableClass))
				.useStoryReporterBuilder(new StoryReporterBuilder().withCodeLocation(CodeLocations
				.codeLocationFromClass(embeddableClass)).withDefaultFormats()
				.withDefaultFormats()
				.withFormats(CONSOLE, TXT, HTML, XML))
				.useParameterConverters(parameterConverters);
	}
	
	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), 
				new EmuMessreihenAnsehenSteps());
	}

	@Override
	public List<String> storyPaths() {
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/*.story", "**/excluded*.story");
	} 
}