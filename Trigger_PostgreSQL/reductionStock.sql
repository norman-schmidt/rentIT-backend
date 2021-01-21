CREATE OR REPLACE FUNCTION reductionStock()
  RETURNS TRIGGER
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	IF NEW.quantity <> 0 THEN
		 UPDATE article_entity SET stock_level = (stock_level - NEW.quantity) WHERE article_id = NEW.article_id;
	END IF;
	RETURN NEW;
END;
$$;;

CREATE TRIGGER reductionStock
  AFTER INSERT
  ON article_quantity_entity
  FOR EACH ROW
  EXECUTE PROCEDURE reductionStock();