package com.sonfl.datahub.bootstrap;

import com.sonfl.datahub.models.Coordinates;
import com.sonfl.datahub.models.ImageryType;
import com.sonfl.datahub.models.Mission;
import com.sonfl.datahub.models.Product;
import com.sonfl.datahub.repositories.CoordinatesRepository;
import com.sonfl.datahub.repositories.MissionRepository;
import com.sonfl.datahub.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class Bootstrap implements CommandLineRunner {

    private MissionRepository missionRepository;
    private ProductRepository productRepository;
    private CoordinatesRepository coordinatesRepository;

    public Bootstrap(MissionRepository missionRepository, ProductRepository productRepository, CoordinatesRepository coordinatesRepository) {
        this.missionRepository = missionRepository;
        this.productRepository = productRepository;
        this.coordinatesRepository = coordinatesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        load();
    }

    @Transactional
    public void load() {
        try {
            Mission mission1 = new Mission();
            mission1.setName("first");
            mission1.setImageryType(ImageryType.HYPERSPECTRAL);
            mission1.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("1981-08-20"));
            mission1.setFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("1981-09-25"));

            Mission mission2 = new Mission();
            mission2.setName("second");
            mission2.setImageryType(ImageryType.MULTISPECTRAL);
            mission2.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-04-19"));
            mission2.setFinishDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-08-18"));

            Product product1 = new Product();
            Coordinates coords1 = new Coordinates(new Point2D.Double(100, 200),
                    new Point2D.Double(200, 210),
                    new Point2D.Double(98, 100),
                    new Point2D.Double(150, 101));

            product1.setCoordinates(coords1);
            product1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("1981-08-22"));
            product1.setMission(mission1);
            product1.setPrice(new BigDecimal(100));
            product1.setPathToProduct("http://somepath.com/pic/21");

            Product product2 = new Product();
            Coordinates coords2 = new Coordinates(new Point2D.Double(300, 500),
                    new Point2D.Double(400, 410),
                    new Point2D.Double(230, 300),
                    new Point2D.Double(250, 500));

            product2.setCoordinates(coords2);
            product2.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-07-13"));
            product2.setMission(mission2);
            product2.setPrice(new BigDecimal(150));
            product2.setPathToProduct("http://somepath.com/pic/22");

            Product product3 = new Product();
            Coordinates coords3 = new Coordinates(new Point2D.Double(350, 550),
                    new Point2D.Double(450, 460),
                    new Point2D.Double(280, 350),
                    new Point2D.Double(300, 550));

            product3.setCoordinates(coords3);
            product3.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-24"));
            product3.setMission(mission2);
            product3.setPrice(new BigDecimal(200));
            product3.setPathToProduct("http://somepath.com/pic/23");

            mission1.getProducts().add(product1);
            mission2.getProducts().add(product2);
            mission2.getProducts().add(product3);

            coordinatesRepository.save(coords1);
            coordinatesRepository.save(coords2);
            coordinatesRepository.save(coords3);
            missionRepository.save(mission1);
            missionRepository.save(mission2);
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
