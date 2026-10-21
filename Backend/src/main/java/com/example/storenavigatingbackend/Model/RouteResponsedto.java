package com.example.storenavigatingbackend.model;

import java.util.List;

public class RouteResponsedto {
    private List<GridPoint> path;     // The green walking corridor points
    private List<ProductPin> pins;    // The actual physical shelf points for red pins

    public RouteResponsedto(List<GridPoint> path, List<ProductPin> pins) {
        this.path = path;
        this.pins = pins;
    }

    // Getters and Setters
    public List<GridPoint> getPath() { return path; }
    public void setPath(List<GridPoint> path) { this.path = path; }

    public List<ProductPin> getPins() { return pins; }
    public void setPins(List<ProductPin> pins) { this.pins = pins; }

    // Inner helper class for the physical product pin
    public static class ProductPin {
        private Long id;
        private String name;
        private int x;
        private int y;

        public ProductPin(Long id, String name, int x, int y) {
            this.id = id;
            this.name = name;
            this.x = x;
            this.y = y;
        }

        // Getters
        public Long getId() { return id; }
        public String getName() { return name; }
        public int getX() { return x; }
        public int getY() { return y; }
    }
}