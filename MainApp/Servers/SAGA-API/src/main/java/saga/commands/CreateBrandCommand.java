package saga.commands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;
import saga.dto.BrandDTO;

public class CreateBrandCommand {
    @TargetAggregateIdentifier
    private Long brandId;
    private BrandDTO brandDTO;

    public CreateBrandCommand(){

    }

    public CreateBrandCommand(Long brandId, BrandDTO brandDTO) {
        this.brandId = brandId;
        this.brandDTO = brandDTO;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public BrandDTO getBrandDTO() {
        return brandDTO;
    }

    public void setBrandDTO(BrandDTO brandDTO) {
        this.brandDTO = brandDTO;
    }

}
