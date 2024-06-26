package com.example.exe202backend.services;

import com.example.exe202backend.dto.OrderItemDTO;
import com.example.exe202backend.dto.OrderItemResponseDTO;
import com.example.exe202backend.mapper.OrderItemMapper;
import com.example.exe202backend.models.OrderItem;
import com.example.exe202backend.repositories.OrderDetailRepository;
import com.example.exe202backend.repositories.OrderItemRepository;
import com.example.exe202backend.repositories.ProductMaterialRepository;
import com.example.exe202backend.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    public List<OrderItemResponseDTO> get(){
        return orderItemRepository.findAll().stream().map(orderItemMapper::toOrderItemResponseDTO).collect(Collectors.toList());
    }

    public ResponseEntity<ResponseObject> create(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem.setOrderDetail(orderDetailRepository.findById(orderItemDTO.getOrderDetailId()).orElse(null));
        orderItem.setProductMaterial(productMaterialRepository.findById(orderItemDTO.getProductMaterialId()).orElse(null));
        orderItemRepository.save(orderItem);
        return ResponseEntity.ok(new ResponseObject("create success",orderItemDTO));
    }

    public Page<OrderItemResponseDTO> getAll(int currentPage, int pageSize, String field){
        Page<OrderItem> orderItems = orderItemRepository.findAll(
                PageRequest.of(currentPage-1,pageSize, Sort.by(Sort.Direction.ASC,field)));
        return orderItems.map(orderItemMapper::toOrderItemResponseDTO);
    }

    public ResponseEntity<ResponseObject> getById(long id){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(()->
                new RuntimeException("Order Item not found"));
        return ResponseEntity.ok(new ResponseObject("get success",orderItemMapper.toOrderItemResponseDTO(orderItem)));
    }
    public ResponseEntity<ResponseObject> delete(long id){
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if(orderItem.isPresent()){
            orderItem.get().setStatus(false);
            orderItemRepository.save(orderItem.get());
            return ResponseEntity.ok(new ResponseObject("delete success",orderItemMapper.toOrderItemResponseDTO(orderItem.get())));
        }
        return ResponseEntity.badRequest().body(new ResponseObject("Order Item not found",null));
    }

    public ResponseEntity<ResponseObject> update(Long id,OrderItemDTO orderItemDTO){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(()->
                new RuntimeException("Order Item not found"));
        if(orderItemDTO.getPrice() == 0){
            orderItem.setPrice(orderItemDTO.getPrice());
        }
        if(orderItemDTO.getQuantity() == 0){
            orderItem.setQuantity(orderItemDTO.getQuantity());
        }
        if(orderItemDTO.getProductMaterialId() == 0){
            orderItemDTO.setProductMaterialId(orderItem.getProductMaterial().getId());
        }
        if(orderItemDTO.getOrderDetailId() == 0){
            orderItemDTO.setOrderDetailId(orderItem.getOrderDetail().getId());
        }
        orderItemDTO.setStatus(orderItem.getStatus());
        orderItemMapper.updateOrderItemFromDto(orderItemDTO,orderItem);
        orderItem.setOrderDetail(orderDetailRepository.findById(orderItemDTO.getOrderDetailId()).orElse(null));
        orderItem.setProductMaterial(productMaterialRepository.findById(orderItemDTO.getProductMaterialId()).orElse(null));
        orderItemRepository.save(orderItem);
        return ResponseEntity.ok(new ResponseObject("update success",orderItemDTO));
    }
    public ResponseEntity<ResponseObject> getOrderItemsByOrderDetailId(Long orderDetailId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderDetailId(orderDetailId);
        if(orderItems.isEmpty()){
            return ResponseEntity.ok(new ResponseObject("Order Item not found",null));
        }
        return ResponseEntity.ok(new ResponseObject("Get success",orderItems
                .stream()
                .map(orderItemMapper::toOrderItemResponseDTO)
                .toList()));
    }
}
