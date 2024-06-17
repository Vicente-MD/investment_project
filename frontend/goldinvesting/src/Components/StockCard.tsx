import React from 'react';
import { Card, CardContent, CardMedia, Typography } from '@mui/material';

interface StockCardProps {
    name: string;
    price: number;
    imageUrl: string;
}

const StockCard: React.FC<StockCardProps> = ({ name, price, imageUrl }) => {
    return (
        <Card sx={{ maxWidth: 345 }}>
            <CardMedia
                component="img"
                height="140"
                image={imageUrl}
                alt={name}
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Price: ${price}
                </Typography>
            </CardContent>
        </Card>
    );
};

export default StockCard;
