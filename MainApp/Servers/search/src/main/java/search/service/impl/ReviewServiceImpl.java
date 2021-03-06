package search.service.impl;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saga.dto.ReviewDTO;
import search.exceptions.ConversionFailedError;
import search.exceptions.EntityNotFound;
import search.model.Review;
import search.model.ReviewStatus;
import search.model.Vehicle;
import search.repository.ReviewRepo;
import search.repository.VehicleRepo;
import search.service.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    VehicleRepo vehicleRepo;

    @Autowired
    DozerBeanMapper mapper;

    @Override
    public ReviewDTO convertToDTO(Review review) throws ConversionFailedError {
        try {
            return mapper.map(review, ReviewDTO.class);
        } catch (Exception e) {
            throw new ConversionFailedError("Internal server error");
        }
    }

    @Override
    public Review convertToModel(ReviewDTO reviewDTO) throws ConversionFailedError {
        try {
            return mapper.map(reviewDTO, Review.class);
        } catch (Exception e) {
            throw new ConversionFailedError("Invalid data");
        }
    }
    @Override
    public List<ReviewDTO> getPending() throws EntityNotFound, ConversionFailedError {

        List<Review> reviews = reviewRepo.findByStatusEquals(ReviewStatus.PENDING);

        return getReviewDTOS(reviews);
    }

    @Override
    public List<ReviewDTO> getByVehicle(Long vehicleId) throws EntityNotFound, ConversionFailedError {

        Optional<Vehicle> vehicle = vehicleRepo.findById(vehicleId);

        if(!vehicle.isPresent()) {
            throw new EntityNotFound("Items not found");
        }

        List<Review> reviews = reviewRepo.findByVehicle(vehicle.get());

        return getReviewDTOS(reviews);
    }

    @Override
    public ReviewDTO add(Long vehicleId, ReviewDTO reviewDTO) throws ConversionFailedError {

        Review newReview = convertToModel(reviewDTO);

        reviewRepo.save(newReview);

        return reviewDTO;
    }

    @Override
    public ReviewDTO getOne(Long vehicleId, Long id) throws EntityNotFound, ConversionFailedError {

        Optional<Vehicle> vehicle = vehicleRepo.findById(vehicleId);

        if(!vehicle.isPresent()) {
            throw new EntityNotFound("Items not found");
        }

        Optional<Review> review = reviewRepo.findByIdAndVehicle(id, vehicle.get());

        if(!review.isPresent()) {
            throw new EntityNotFound("Items not found");
        }

        return convertToDTO(review.get());
    }

    @Override
    public ReviewDTO update(Long vehicleId, Long id, ReviewDTO reviewDTO) throws EntityNotFound, ConversionFailedError {

        Optional<Vehicle> vehicle = vehicleRepo.findById(vehicleId);

        if(!vehicle.isPresent()) {
            throw new EntityNotFound("Items not found");
        }

        Optional<Review> review = reviewRepo.findByIdAndVehicle(id, vehicle.get());

        if(!review.isPresent()) {
            throw new EntityNotFound("Items not found");
        }

        Review updated = convertToModel(reviewDTO);
        updated.setId(id);

        reviewRepo.save(updated);
        return reviewDTO;
    }

    @Override
    public ReviewDTO delete(Long vehicleId, Long id) throws EntityNotFound, ConversionFailedError {

        Optional<Vehicle> vehicle = vehicleRepo.findById(vehicleId);

        if(!vehicle.isPresent()) {
            throw new EntityNotFound("Items not found");
        }

        Optional<Review> deleted = reviewRepo.findByIdAndVehicle(id, vehicle.get());

        if(!deleted.isPresent()) {
            throw new EntityNotFound("Items not found");
        }
        deleted.get().setDeleted(true);
        reviewRepo.save(deleted.get());
        // reviewRepo.deleteById(id);
        return convertToDTO(deleted.get());
    }

    private List<ReviewDTO> getReviewDTOS(List<Review> reviews) throws EntityNotFound, ConversionFailedError {
        if (reviews.isEmpty()) {
            throw new EntityNotFound("Items not found");
        }

        List<ReviewDTO> reviewDTOS = new ArrayList<>();

        for (Review r : reviews) {
            reviewDTOS.add(convertToDTO(r));
        }

        return reviewDTOS;
    }
}
