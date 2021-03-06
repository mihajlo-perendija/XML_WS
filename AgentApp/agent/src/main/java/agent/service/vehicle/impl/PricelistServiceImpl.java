package agent.service.vehicle.impl;

import agent.dto.shared.PricelistDTO;
import agent.exceptions.ConversionFailedError;
import agent.exceptions.DuplicateEntity;
import agent.exceptions.EntityNotFound;
import agent.model.vehicle.Pricelist;
import agent.repository.vehicle.PricelistRepo;
import agent.repository.vehicle.VehicleRepo;
import agent.service.vehicle.PricelistService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PricelistServiceImpl implements PricelistService {

    @Autowired
    PricelistRepo pricelistRepo;

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    DozerBeanMapper mapper;

    @Override
    public PricelistDTO convertToDTO(Pricelist pricelist) throws ConversionFailedError {
        try {
            return mapper.map(pricelist, PricelistDTO.class);
        } catch (Exception e) {
            throw new ConversionFailedError("Internal server error");
        }
    }

    @Override
    public Pricelist convertToModel(PricelistDTO pricelistDTO) throws ConversionFailedError {
        try {
            return mapper.map(pricelistDTO, Pricelist.class);
        } catch (Exception e) {
            throw new ConversionFailedError("Invalid data");
        }
    }

    @Override
    public PricelistDTO add(PricelistDTO pricelistDTO, Boolean isAgent) throws ConversionFailedError, DuplicateEntity {

        Pricelist newPricelist = convertToModel(pricelistDTO);

        if(!isAgent) {
            newPricelist.setDiscount(0);
            newPricelist.setPenalty(0);
        }

        if (!pricelistRepo.existsByNameAndOwnerId(pricelistDTO.getName(), pricelistDTO.getOwnerId())) {
            Pricelist savedPriceList = pricelistRepo.save(newPricelist);
            return convertToDTO(savedPriceList);
        } else {
            throw new DuplicateEntity("Item already exists");
        }
    }

    @Override
    public PricelistDTO getOne(Long id) throws EntityNotFound, ConversionFailedError {

        Optional<Pricelist> pricelist = pricelistRepo.findById(id);

        if (!pricelist.isPresent()) {
            throw new EntityNotFound("No item with ID: " + id);
        }else {
            return convertToDTO(pricelist.get());
        }
    }

    @Override
    public PricelistDTO update(Long id, PricelistDTO pricelistDTO) throws EntityNotFound, DuplicateEntity, ConversionFailedError {

        Optional<Pricelist> change = pricelistRepo.findById(id);

        if (!change.isPresent()) {
            throw new EntityNotFound("No item with ID: " + id);
        }
        if (vehicleRepo.existsByPricelist(change.get())) {
            throw new DuplicateEntity("Unable to change item");
        }
        Pricelist changed = convertToModel(pricelistDTO);
        changed.setId(id);

        Pricelist savedPriceList = pricelistRepo.save(changed);

        return pricelistDTO;
    }

    @Override
    @Transactional
    public PricelistDTO delete(Long id) throws EntityNotFound, DuplicateEntity, ConversionFailedError {

        Optional<Pricelist> deleted = pricelistRepo.findById(id);

        if (!deleted.isPresent()) {
            throw new EntityNotFound("No item with ID: " + id);
        } else if (vehicleRepo.existsByPricelist(deleted.get())) {
            throw new DuplicateEntity("Unable to delete item");
        }
        deleted.get().setDeleted(true);
        pricelistRepo.save(deleted.get());

        return convertToDTO(deleted.get());
    }

    // For saga rollback purpose
    public PricelistDTO deletePermanent(Long id) throws EntityNotFound, DuplicateEntity, ConversionFailedError {

        Optional<Pricelist> deleted = pricelistRepo.findById(id);

        if (!deleted.isPresent()) {
            throw new EntityNotFound("No item with ID: " + id);
        } else if (vehicleRepo.existsByPricelist(deleted.get())) {
            throw new DuplicateEntity("Unable to delete item");
        }
        pricelistRepo.deleteById(id);

        return convertToDTO(deleted.get());
    }
    @Override
    public List<PricelistDTO> getByOwner(Long ownerId) throws EntityNotFound, ConversionFailedError {

        List<Pricelist> pricelists = pricelistRepo.findAllByOwnerId(ownerId);

        List<PricelistDTO> pricelistDTOS = new ArrayList<>();

        for (Pricelist p : pricelists) {
            pricelistDTOS.add(convertToDTO(p));
        }

        return pricelistDTOS;
    }
}
