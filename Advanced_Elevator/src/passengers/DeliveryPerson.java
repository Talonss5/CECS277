/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passengers;

import elevators.Simulation;
import java.util.*;

/**
 *
 * @author votha
 */
public class DeliveryPerson implements PassengerFactory{
    private int weight=2;
    
    public void setWeight(int num){
        weight=num;
    }
    
    @Override
    public String factoryName() {
        return"DeliveryPerson";
    }

    @Override
    public String shortName() {
        return"DP";
    }

    @Override
    public int factoryWeight() {
        return weight;
    }
    

    @Override
    public BoardingStrategy createBoardingStrategy(Simulation simulation) {
        return new ThresholdBoarding(5);
    }

    @Override
    public TravelStrategy createTravelStrategy(Simulation simulation) {
        Random ran=simulation.getRandom();
        ArrayList<Integer> destination=new ArrayList<>();
        ArrayList<Long> schedule=new ArrayList<>();
        int FloorNeedVisit=ran.nextInt(simulation.getBuilding().getFloorCount()*2/3)+1;
        int newFloor;
        for(int i=0; i<FloorNeedVisit;i++){
            newFloor=ran.nextInt(simulation.getBuilding().getFloorCount()-1)+2;
            while(destination.contains(newFloor)){
                newFloor=ran.nextInt(simulation.getBuilding().getFloorCount()-1)+2;
            }
            destination.add(newFloor);
            
        }
        for(int i=0; i<FloorNeedVisit;i++){
            double tempschedule=10*ran.nextGaussian()+60;
            schedule.add((long)tempschedule);
        }
        
        return new MultipleDestinationTravel(destination,schedule);
    }

    @Override
    public EmbarkingStrategy createEmbarkingStrategy(Simulation simulation) {
        return new ResponsibleEmbarking();
    }

    @Override
    public DebarkingStrategy createDebarkingStrategy(Simulation simulation) {
        return new DistractedDebarking();
    }
    
}
