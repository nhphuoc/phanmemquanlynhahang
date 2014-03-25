DROP TABLE a;
DROP TABLE b;
DROP TABLE c;
CREATE TEMPORARY TABLE a AS(
SELECT  DATE(invoice.date) AS d, SUM(cost) AS s
FROM invoice
GROUP BY DATE(invoice.date)
);
CREATE TEMPORARY TABLE b AS(
SELECT  DATE(raw_material_invoice.date) AS d,SUM(number*cost) AS s
 FROM raw_material_invoice
JOIN raw_material_invoice_detail
ON raw_material_invoice.id=raw_material_invoice_detail.id
GROUP BY DATE(raw_material_invoice.date)
);
CREATE TEMPORARY TABLE c AS(
SELECT DATE(payment_invoice.date) AS d,SUM(cost) AS s
FROM payment_invoice
GROUP BY DATE(payment_invoice.date)
);
SELECT
	a.d AS d,
	IF(a.s+'' IS NULL,0,a.s) AS s1,
	IF(b.s+'' IS NULL,0,b.s) AS s2,
	(IF(a.s+'' IS NULL,0,a.s)-IF(b.s+'' IS NULL,0,b.s))	AS s
FROM a LEFT OUTER JOIN b USING (d)
