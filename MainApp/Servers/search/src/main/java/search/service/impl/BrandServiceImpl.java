package search.service.impl;

import com.netflix.discovery.converters.Auto;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import search.dto.BrandDTO;
import search.exceptions.ConversionFailedError;
import search.exceptions.DuplicateEntity;
import search.exceptions.EntityNotFound;
import search.exceptions.UnexpectedError;
import search.model.Brand;
import search.repository.BrandRepo;
import search.service.BrandService;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepo brandRepo;

    @Override
    public BrandDTO convertToDTO(Brand brand) throws ConversionFailedError {
        return new DozerBeanMapper().map(brand, BrandDTO.class);
    }

    @Override
    public Brand convertToModel(BrandDTO brandDTO) throws ConversionFailedError {
        return new DozerBeanMapper().map(brandDTO, Brand.class);
    }

    @Override
    public BrandDTO add(BrandDTO brandDTO) throws DuplicateEntity {
        return null;
    }

    @Override
    public BrandDTO getOne(Long id) throws EntityNotFound {
        return null;
    }

    @Override
    public List<BrandDTO> getAll() throws EntityNotFound {
        return null;
    }

    @Override
    public BrandDTO update(Long id, BrandDTO brandDTO) throws UnexpectedError {
        return null;
    }

    @Override
    public BrandDTO delete(Long id) throws EntityNotFound {
        return null;
    }
}
