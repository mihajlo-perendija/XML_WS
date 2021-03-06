package saga.commands.vehiclePartsCommands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;
import saga.commands.TypeOfCommand;
import saga.dto.CategoryDTO;
import saga.dto.FuelDTO;

public class MainFuelCommand {
    @TargetAggregateIdentifier
    private Long fuelId;
    private FuelDTO fuelDTO;
    private TypeOfCommand typeOfCommand;

    public MainFuelCommand(){

    }

    public MainFuelCommand(Long fuelId, FuelDTO fuelDTO, TypeOfCommand typeOfCommand) {
        this.fuelId = fuelId;
        this.fuelDTO = fuelDTO;
        this.typeOfCommand = typeOfCommand;
    }

    public Long getFuelId() {
        return fuelId;
    }

    public void setFuelId(Long fuelId) {
        this.fuelId = fuelId;
    }

    public FuelDTO getFuelDTO() {
        return fuelDTO;
    }

    public void setFuelDTO(FuelDTO fuelDTO) {
        this.fuelDTO = fuelDTO;
    }

    public TypeOfCommand getTypeOfCommand() {
        return typeOfCommand;
    }

    public void setTypeOfCommand(TypeOfCommand typeOfCommand) {
        this.typeOfCommand = typeOfCommand;
    }
}
