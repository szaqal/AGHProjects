package genetic.tasks;

import genetic.Individual;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import api.annotations.Computable;
import api.annotations.Value;
import api.computation.ComputableTask;
import api.computation.ComputationContext;

@Computable(taskName="PopulationCreator")
public class PopulationCreatorTask implements ComputableTask {

	
	@Value(name="population")
	private Collection<Individual> population;
	
	@Value(name="populationSize")
	private int populationSize;
	
	@Value(name="generationsCount")
	private int generationsCount;
	
	@api.annotations.ComputationContext
	private ComputationContext computationContext;
	
	/** {@inheritDoc} */
	public void doComputation() {
		if(population == null) {
			population = new ArrayList<Individual>();
		}
		for(int i=0;i<populationSize;i++) {
			population.add(new Individual(computationContext.getSetting("individualLowerBound"),
					computationContext.getSetting("individualUpperBound"), computationContext.getSetting("individualVariableCount")));
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		PopulationCreatorTask task = new PopulationCreatorTask();
		ComputationContext context  = new ComputationContext();
		context.addSetting("individualLowerBound", "-5");
		context.addSetting("individualUpperBound", "5");
		context.addSetting("individualVariableCount", "30");
		task.setPopulationSize(100000);
		task.setComputationContext(context);
		task.doComputation();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("/home/malczyk/plik4.txt")));
		out.writeObject(task.getPopulation());
	}

	/**
	 * Getter.
	 * @return the population
	 */
	public Collection<Individual> getPopulation() {
		return population;
	}

	/**
	 * Setter.
	 * @param population the population to set
	 */
	public void setPopulation(Collection<Individual> population) {
		this.population = population;
	}

	/**
	 * Getter.
	 * @return the populationSize
	 */
	public int getPopulationSize() {
		return populationSize;
	}

	/**
	 * Setter.
	 * @param populationSize the populationSize to set
	 */
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	/**
	 * Getter.
	 * @return the generationsCount
	 */
	public int getGenerationsCount() {
		return generationsCount;
	}

	/**
	 * Setter.
	 * @param generationsCount the generationsCount to set
	 */
	public void setGenerationsCount(int generationsCount) {
		this.generationsCount = generationsCount;
	}

	/**
	 * Getter.
	 * @return the computationContext
	 */
	public ComputationContext getComputationContext() {
		return computationContext;
	}

	/**
	 * Setter.
	 * @param computationContext the computationContext to set
	 */
	public void setComputationContext(ComputationContext computationContext) {
		this.computationContext = computationContext;
	}

}
